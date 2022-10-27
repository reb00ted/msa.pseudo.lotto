package msa.pseudo.lotto.statisticsservice.service;

import lombok.RequiredArgsConstructor;
import msa.pseudo.lotto.statisticsservice.domain.LottoResult;
import msa.pseudo.lotto.statisticsservice.domain.User;
import msa.pseudo.lotto.statisticsservice.dto.StatisticsDto;
import msa.pseudo.lotto.statisticsservice.dto.UserDto;
import msa.pseudo.lotto.statisticsservice.kafka.event.LottoResultEvent;
import msa.pseudo.lotto.statisticsservice.kafka.event.UserCreationEvent;
import msa.pseudo.lotto.statisticsservice.redis.Statistics;
import msa.pseudo.lotto.statisticsservice.redis.StatisticsRepository;
import msa.pseudo.lotto.statisticsservice.repository.LottoResultRepository;
import msa.pseudo.lotto.statisticsservice.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final UserRepository userRepository;
    private final LottoResultRepository lottoResultRepository;
    private final StatisticsRepository statisticsRepository;

    @Transactional
    public void update(LottoResultEvent lottoResultEvent) {
        User user = userRepository.findByUserId(lottoResultEvent.getUserId());
        if (user == null) {
            throw new IllegalStateException();
        }
        if (lottoResultRepository.existsById(lottoResultEvent.getTxId())) {
            return;
        }

        LottoResult lottoResult = new LottoResult(user, lottoResultEvent);
        user.spend(lottoResultEvent.getSpend());
        user.profit(lottoResultEvent.getProfit());
        lottoResultRepository.save(lottoResult);
    }

    @Transactional
    public void join(UserCreationEvent userCreationEvent) {
        String userId = userCreationEvent.getUserId();
        if (userRepository.findById(userId).isEmpty()) {
            userRepository.save(new User(userId));
        }
    }

    @Transactional
    public void updateLucky() {
        Page<User> lucky = userRepository.findByOrderByTotalDesc(PageRequest.of(0, 10));
        List<UserDto> data = lucky.get()
                .filter(user -> user.getTotal() > 0L)
                .map(UserDto::new)
                .collect(Collectors.toList());
        System.out.println(data.size());
        if (data.size() < 10) {
            data.add(new UserDto("비회원", 0L, 0L, 0L));
        }

        statisticsRepository.save(new Statistics("lucky", data));
    }

    @Transactional
    public void updateUnlucky() {
        Page<User> unlucky = userRepository.findByOrderByTotalAsc(PageRequest.of(0, 10));
        List<UserDto> data = unlucky.get()
                .filter(user -> user.getTotal() < 0L)
                .map(UserDto::new)
                .collect(Collectors.toList());
        System.out.println(data.size());
        statisticsRepository.save(new Statistics("unlucky", data));
    }

    @Transactional(readOnly = true)
    public StatisticsDto getLucky() {
        Optional<Statistics> lucky = statisticsRepository.findById("lucky");
        if (lucky.isEmpty()) {
            return new StatisticsDto();
        }
        return new StatisticsDto(lucky.get());
    }

    @Transactional(readOnly = true)
    public StatisticsDto getUnlucky() {
        Optional<Statistics> unlucky = statisticsRepository.findById("unlucky");
        if (unlucky.isEmpty()) {
            System.out.println("empty");
            return new StatisticsDto();
        }
        return new StatisticsDto(unlucky.get());
    }
}
