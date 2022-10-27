package msa.pseudo.lotto.lottoservice.kafka;

import lombok.RequiredArgsConstructor;
import msa.pseudo.lotto.lottoservice.kafka.event.LottoResultEvent;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;

@Component
@RequiredArgsConstructor
public class LottoResultProducer {

    private final StreamBridge streamBridge;

    public void produce(LottoResultEvent lottoResultEvent) {
        System.out.printf("produce %s\n", lottoResultEvent.toString());
        streamBridge.send("producer-out-0", lottoResultEvent, MimeTypeUtils.APPLICATION_JSON);
    }
}
