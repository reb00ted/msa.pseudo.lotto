package msa.pseudo.lotto.pointservice.kafka.event;

import lombok.Data;

@Data
public class UserCreationEvent {
    String userId;
}
