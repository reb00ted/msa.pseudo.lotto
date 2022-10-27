package msa.pseudo.lotto.lottoservice.service;

import lombok.RequiredArgsConstructor;
import msa.pseudo.lotto.lottoservice.domain.Outbox;
import msa.pseudo.lotto.lottoservice.kafka.LottoResultProducer;
import msa.pseudo.lotto.lottoservice.kafka.event.LottoResultEvent;
import msa.pseudo.lotto.lottoservice.repository.OutboxRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OutboxService {

    private final LottoResultProducer lottoResultProducer;
    private final OutboxRepository outboxRepository;

    @Transactional
    public void publishLottoResultEvent() {
        List<Outbox> toPublishList = outboxRepository.findToPublishList();
        for (Outbox outbox : toPublishList) {
            lottoResultProducer.produce(new LottoResultEvent(outbox));
            outbox.complete();
        }
    }
}
