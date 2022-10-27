package msa.pseudo.lotto.userserivce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import msa.pseudo.lotto.userserivce.model.User;

import java.time.LocalDateTime;

@Data @NoArgsConstructor @AllArgsConstructor
public class JoinResponse {

    String userId;
    LocalDateTime createdAt;

    public JoinResponse(User user) {
        this.userId = user.getUserId();
        this.createdAt = user.getCreatedAt();
    }
}
