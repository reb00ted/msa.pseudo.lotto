package msa.pseudo.lotto.saga.kafka;

import lombok.RequiredArgsConstructor;
import msa.pseudo.lotto.saga.domain.User;
import msa.pseudo.lotto.saga.kafka.event.UserCreationEvent;
import msa.pseudo.lotto.saga.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
public class KafkaConfig {

    private final UserRepository userRepository;

    @Bean
    public Consumer<UserCreationEvent> userCreationEventConsumer() {
        return userCreationEvent -> userRepository.save(new User(userCreationEvent.getUserId()));
    }
}
