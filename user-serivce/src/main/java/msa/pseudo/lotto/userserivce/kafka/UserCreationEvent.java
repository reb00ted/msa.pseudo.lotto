package msa.pseudo.lotto.userserivce.kafka;


import lombok.Data;
import lombok.Getter;

@Data
public class UserCreationEvent {
    private String userId;

    public UserCreationEvent(String userId) {
        this.userId = userId;
    }
}
