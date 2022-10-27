package msa.pseudo.lotto.userserivce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import msa.pseudo.lotto.userserivce.model.User;

import java.time.LocalDateTime;

@Data @NoArgsConstructor @AllArgsConstructor
public class LoginResponse {

    private String userId;
    private String lastLoginAt;

    public LoginResponse(User user) {
        this.userId = user.getUserId();
        this.lastLoginAt = user.getLastLoginAt().toString();
    }

    public LoginResponse(String userId, LocalDateTime lastLoginAt) {
        this.userId = userId;
        this.lastLoginAt = lastLoginAt.toString();
    }
}
