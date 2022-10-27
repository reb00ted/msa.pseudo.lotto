package msa.pseudo.lotto.webclient.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data @NoArgsConstructor @AllArgsConstructor
public class LoginRequest {
    @NotNull
    @Length(min = 4, max = 16)
    String userId;

    @NotNull
    @Length(min = 4, max = 24)
    String password;
}
