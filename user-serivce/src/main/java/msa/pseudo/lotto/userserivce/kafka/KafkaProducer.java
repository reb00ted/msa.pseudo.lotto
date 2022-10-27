package msa.pseudo.lotto.userserivce.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;

@Component
@RequiredArgsConstructor
public class KafkaProducer {

    private final StreamBridge streamBridge;

    public void sendMessage(UserCreationEvent data) {
        System.out.println(data.toString());
        streamBridge.send("producer-out-0", data, MimeTypeUtils.APPLICATION_JSON);
    }
}
