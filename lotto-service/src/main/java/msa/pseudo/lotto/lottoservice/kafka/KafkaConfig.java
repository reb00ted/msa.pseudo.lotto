package msa.pseudo.lotto.lottoservice.kafka;

import lombok.RequiredArgsConstructor;
import msa.pseudo.lotto.lottoservice.domain.User;
import msa.pseudo.lotto.lottoservice.kafka.event.UserCreationEvent;
import msa.pseudo.lotto.lottoservice.repository.UserRepository;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
public class KafkaConfig {
    private final UserRepository userRepository;

    @Bean
    public Consumer<UserCreationEvent> userCreationEvent() {

        return userCreationEvent -> {
            System.out.println(userCreationEvent);
            userRepository.save(new User(userCreationEvent.getUserId()));
        };
    }


}
