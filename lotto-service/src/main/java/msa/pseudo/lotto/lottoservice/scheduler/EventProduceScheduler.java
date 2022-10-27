package msa.pseudo.lotto.lottoservice.scheduler;

import lombok.RequiredArgsConstructor;
import msa.pseudo.lotto.lottoservice.kafka.LottoResultProducer;
import msa.pseudo.lotto.lottoservice.service.OutboxService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@RequiredArgsConstructor
public class EventProduceScheduler {

    private final OutboxService outboxService;

    @Scheduled(cron = "0 * * * * *")
    public void produceLottoResultEvent() {
        outboxService.publishLottoResultEvent();
    }
}
