package msa.pseudo.lotto.pointservice.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import msa.pseudo.lotto.pointservice.kafka.event.LottoResultEvent;
import msa.pseudo.lotto.pointservice.model.User;
import msa.pseudo.lotto.pointservice.kafka.event.UserCreationEvent;
import msa.pseudo.lotto.pointservice.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
public class KafkaConsumerConfig {

    private final UserRepository userRepository;

    @Bean
    public Consumer<UserCreationEvent> userCreationEventConsumer() {
        return userCreationEvent -> userRepository.save(new User(userCreationEvent.getUserId()));
    }

    @Bean
    public Consumer<LottoResultEvent> lottoResultEventConsumer() {
        return lottoResultEvent -> System.out.println(lottoResultEvent);
    }


}
