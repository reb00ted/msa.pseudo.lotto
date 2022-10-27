package msa.pseudo.lotto.lottoservice.kafka.event;

import lombok.Data;

@Data
public class UserCreationEvent {
    private String userId;
}
