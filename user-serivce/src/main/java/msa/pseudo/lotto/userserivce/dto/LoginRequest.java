package msa.pseudo.lotto.userserivce.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class LoginRequest {

    @NotNull(message = "ID를 입력해주세요.")
    @Size(min = 4, max = 16, message = "ID의 길이는 4 ~ 16글자 입니다.")
    String userId;

    @NotNull(message = "패스워드를 입력해주세요.")
    @Size(min = 6, max = 24, message = "패스워드의 길이는 6 ~ 24글자 입니다.")
    String password;
}
