package msa.pseudo.lotto.webclient.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data @NoArgsConstructor @AllArgsConstructor
public class JoinResponse {
    String userId;
    LocalDateTime createdAt;
    String message;

    public JoinResponse(String message) {
        this.message = message;
    }
}
