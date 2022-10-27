package msa.pseudo.lotto.webclient.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseCookie;

@Data @NoArgsConstructor @AllArgsConstructor
public class LoginResponse {
    private String userId;
    private String lastLoginAt;
    private String sessionId;
    private ResponseCookie cookie;
    private String message;


    public LoginResponse(String message) {
        this.message = message;
    }

}
