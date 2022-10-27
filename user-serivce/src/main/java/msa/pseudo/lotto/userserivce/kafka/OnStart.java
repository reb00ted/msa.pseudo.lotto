package msa.pseudo.lotto.userserivce.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OnStart implements ApplicationRunner {

    private final KafkaProducerScheduler kafkaProducerScheduler;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        kafkaProducerScheduler.publishUserCreationEvent();
    }
}
