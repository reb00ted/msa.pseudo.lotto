package msa.pseudo.lotto.saga.kafka.event;

import lombok.Data;

@Data
public class UserCreationEvent {
    private String userId;
}
