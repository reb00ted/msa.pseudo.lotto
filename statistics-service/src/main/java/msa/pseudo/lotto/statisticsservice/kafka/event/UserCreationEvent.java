package msa.pseudo.lotto.statisticsservice.kafka.event;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class UserCreationEvent {
    String userId;
}
