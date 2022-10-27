package msa.pseudo.lotto.lottoservice.service;

import lombok.RequiredArgsConstructor;
import msa.pseudo.lotto.lottoservice.common.NotFoundException;
import msa.pseudo.lotto.lottoservice.domain.*;
import msa.pseudo.lotto.lottoservice.dto.*;
import msa.pseudo.lotto.lottoservice.repository.LottoRepository;
import msa.pseudo.lotto.lottoservice.repository.LottoRoundRepository;
import msa.pseudo.lotto.lottoservice.repository.OutboxRepository;
import msa.pseudo.lotto.lottoservice.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LottoService {

    private final UserRepository userRepository;
    private final LottoRepository lottoRepository;
    private final LottoRoundRepository lottoRoundRepository;
    private final OutboxRepository outboxRepository;

    private final Random random = new Random();

    @Transactional(readOnly = true)
    public LottoRoundResponse getFinishedLottoRound(Integer round) {
        if (round == 0) {
            return getLastRound();
        }

        LottoRound lottoRound = lottoRoundRepository.findByRound(round).orElseThrow(() -> new NotFoundException());
        if (lottoRound.getBonusNumber() == null) {
            throw new NotFoundException();
        }

        LottoRound byRoundWithWinningNumbers = lottoRoundRepository.findByRoundWithWinningNumbers(round);
        LottoRound byRoundWithWinnings = lottoRoundRepository.findByRoundWithWinnings(round);
        LottoRoundResponse result = new LottoRoundResponse(lottoRound);
        result.setWinningNumbers(byRoundWithWinningNumbers.getWinningNumbers());
        result.setWinnings(byRoundWithWinnings.getWinnings());
        return result;
    }

    @Transactional
    public LottoBuyResponse buy(LottoBuyRequest request) {
        LottoRound lottoRound = lottoRoundRepository.findByRound(request.getLottoRound())
                .orElseThrow(() -> new IllegalArgumentException());
        LocalDateTime requestAt = request.getRequestAt();
        LocalDateTime start = lottoRound.getStartDate();
        LocalDateTime end = lottoRound.getEndDate();
        
        // 유효한 구매 가능한 시간에 요청이 온것이 아니라면 거부
        if (requestAt.isBefore(start) || requestAt.isAfter(end)) {
            throw new IllegalStateException();
        }

        Lotto lotto;
        System.out.println(request);
        System.out.println(request.getUserId());
        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new IllegalArgumentException());
        lotto = new Lotto(request.getTxId(), user, lottoRound, request.getPrice());
        lotto.setLottoNumbers(request.getLottoNumbers()); // 생성자에서 this 참조 사용하지 않기 위해 setter 따로 설정
        lotto = lottoRepository.save(lotto);
        // 구매 가능한 시간에 구입 신청을 했었지만 지연 또는 장애가 발생해서 구입 시점에 이미 종료된 복권 회차인 경우 즉시 정산
        if (lottoRound.getBonusNumber() != null) {
            match(lotto, lottoRound);
        }
        return new LottoBuyResponse(lotto);
    }

    @Transactional(readOnly = true)
    public Optional<LottoRoundResponse> getCurrentRound() {
        LottoRound lottoRound = lottoRoundRepository.findTopByOrderByRoundDesc();
        if (lottoRound == null) {
            return Optional.empty();
        }
        return Optional.of(new LottoRoundResponse(lottoRound));
    }

    @Transactional(readOnly = true)
    public LottoRoundResponse getLastRound() {
        LottoRound lottoRound = lottoRoundRepository.findTopByOrderByRoundDesc();
        if (lottoRound == null || lottoRound.getRound() == 1) {
            throw new NotFoundException();
        }
        return getFinishedLottoRound(lottoRound.getRound() - 1);
    }


    @Transactional
    public void finishCurrentRound() {
        LottoRound lottoRound = lottoRoundRepository.findTopByOrderByRoundDesc();
        // 로또 회차 정보가 없거나 종료 시각 이전에 호출된 경우 예외 처리
        if (lottoRound == null || LocalDateTime.now().isBefore(lottoRound.getEndDate())) {
            throw new IllegalStateException();
        }

        // 당첨 번호, 당첨금 추첨
        Set<Integer> winningNumbers = drawWinningNumbers();
        int bonusNumber = drawBonusNumber(winningNumbers);
        List<Long> winnings = drawWinnings();
        lottoRound.publishWinningNumbers(winningNumbers);
        lottoRound.publishBonusNumber(bonusNumber);
        lottoRound.publishWinnings(winnings);
        lottoRoundRepository.save(lottoRound);

        // 해당 회차 복권들 정산
        List<Lotto> lottoList = lottoRepository.findByRound(lottoRound.getRound());
        match(lottoList, lottoRound);
        makeNextRound(lottoRound.getRound() + 1);
    }

    @Transactional
    public LottoRoundDto makeNextRound(int round) {
        LocalDateTime now = LocalDateTime.now();
        if (now.getMinute() >= 55) {
            now = now.plusHours(1);
        }

        LocalDateTime start = LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), now.getHour(), 0);
        LocalDateTime end = LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), now.getHour(), 55);
        LottoRound lottoRound = new LottoRound(round, start, end);
        lottoRound = lottoRoundRepository.save(lottoRound);
        return new LottoRoundDto(lottoRound);
    }

    @Transactional(readOnly = true)
    public LottoRoundDto getRoundResult(int round) {
        LottoRound byRoundWithWinnings = lottoRoundRepository.findByRoundWithWinnings(round);
        LottoRound byRoundWithWinningNumbers = lottoRoundRepository.findByRoundWithWinningNumbers(round);

        LottoRoundDto result = new LottoRoundDto(byRoundWithWinningNumbers);
        result.setWinningNumbers(byRoundWithWinningNumbers.getWinningNumbers());
        result.setWinnings(byRoundWithWinnings.getWinnings());
        return result;
    }

    @Transactional(readOnly = true)
    public LottoListResponse getLottoList(String userId, Pageable pageable) {
        System.out.printf("%s %s\n", userId, pageable.toString());
        Page<Lotto> data = lottoRepository.findByUserUserIdOrderByTxIdDesc(userId, pageable);
        System.out.println(lottoRepository.findByUserUserId(userId));
        System.out.println(data.getNumberOfElements());
        System.out.println(data.getTotalPages());
        System.out.println(data.getTotalElements());
        System.out.println(data.getSize());
        return new LottoListResponse(data);
    }


    private Set<Integer> drawWinningNumbers() {
        Set<Integer> result = new HashSet<>();
        while (result.size() < 6) {
            result.add(random.nextInt(45) + 1);
        }
        return result;
    }

    private int drawBonusNumber(Set<Integer> winningNumbers) {
        int result = random.nextInt(45) + 1;
        while (winningNumbers.contains(result)) {
            result = random.nextInt(45) + 1;
        }
        return result;
    }

    private List<Long> drawWinnings() {
        List<Long> winningsUpperBound = List.of(100_000L, 10_000_000L, 100_000_000L, 10_000_000_000L);
        List<Long> result = new ArrayList<>();
        result.add(0L);
        result.add(5000L);
        long lowerBound = 5000L;
        for (Long upperBound : winningsUpperBound) {
            long winnings = random.nextLong(lowerBound, upperBound);
            result.add(winnings);
            lowerBound = upperBound;
        }

        Collections.reverse(result);
        return result;
    }

    private void match(Lotto lotto, LottoRound lottoRound) {
        // @Transactional 메서드 내부에서만 호출해야함
        match(List.of(lotto), lottoRound);
    }

    private void match(List<Lotto> lottoList, LottoRound lottoRound) {
        // @Transactional 메서드 내부에서만 호출해야함
        if (lottoList == null || lottoList.isEmpty()) return;

        int round = lottoList.get(0).getRound().getRound();
        Set<Integer> winningNumbers = lottoRound.getWinningNumbersAsIntegerSet();
        int bonusNumber = lottoRound.getBonusNumber();
        List<Long> winnings = lottoRound.getWinningsAsLongList();

        for (Lotto lotto : lottoList) {
            int count = 0;
            boolean bonusMatched = false;
            for (LottoNumber lottoNumber : lotto.getNumbers()) {
                int num = lottoNumber.getId().getNumber();
                if (winningNumbers.contains(num)) {
                    count++;
                    lottoNumber.match();
                } else if (num == bonusNumber) {
                    bonusMatched = true;
                } else {
                    lottoNumber.notMatched();
                }
            }

            int ranking = Math.min((7 - count) + ((count == 6 || (count == 5 && bonusMatched)) ? 0 : 1), 6);
            long currentWinnings = winnings.get(ranking - 1);
            lotto.finish(ranking, currentWinnings);
            Outbox outbox = new Outbox(lotto);
            System.out.println(outbox.toString());
            outboxRepository.save(outbox);
        }
    }
}
