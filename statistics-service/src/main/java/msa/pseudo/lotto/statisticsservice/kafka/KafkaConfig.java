package msa.pseudo.lotto.statisticsservice.kafka;

import lombok.RequiredArgsConstructor;
import msa.pseudo.lotto.statisticsservice.kafka.event.LottoResultEvent;
import msa.pseudo.lotto.statisticsservice.kafka.event.UserCreationEvent;
import msa.pseudo.lotto.statisticsservice.service.StatisticsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
public class KafkaConfig {

    private final StatisticsService statisticsService;

    @Bean
    public Consumer<UserCreationEvent> userCreationEventConsumer() {
        return userCreationEvent -> {
            System.out.println(userCreationEvent);
            statisticsService.join(userCreationEvent);
        };
    }

    @Bean
    public Consumer<LottoResultEvent> lottoResultEventConsumer() {
        return lottoResultEvent -> {
            System.out.println(lottoResultEvent);
            statisticsService.update(lottoResultEvent);
        };
    }
}
