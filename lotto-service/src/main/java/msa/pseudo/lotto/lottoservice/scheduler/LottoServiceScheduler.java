package msa.pseudo.lotto.lottoservice.scheduler;

import lombok.RequiredArgsConstructor;
import msa.pseudo.lotto.lottoservice.dto.LottoRoundDto;
import msa.pseudo.lotto.lottoservice.service.LottoService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LottoServiceScheduler {

    private final LottoService lottoService;

    @Scheduled(cron = "0 55 * * * *")
    public void finishLastRound() {
        lottoService.finishCurrentRound();
    }



}
