package msa.pseudo.lotto.lottoservice.scheduler;

import lombok.RequiredArgsConstructor;
import msa.pseudo.lotto.lottoservice.domain.LottoRound;
import msa.pseudo.lotto.lottoservice.dto.LottoRoundDto;
import msa.pseudo.lotto.lottoservice.dto.LottoRoundResponse;
import msa.pseudo.lotto.lottoservice.service.LottoService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OnStart implements ApplicationRunner {

    private final LottoService lottoService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("onStart");
        Optional<LottoRoundResponse> currentRound = lottoService.getCurrentRound();
        if (currentRound.isEmpty()) {
            System.out.println("make next round 1");
            lottoService.makeNextRound(1);
            return;
        }

        LottoRoundResponse lottoRound = currentRound.get();
        if (lottoRound.getEndDate().isBefore(LocalDateTime.now())) {
            lottoService.finishCurrentRound();
        }
        return;
    }
}
