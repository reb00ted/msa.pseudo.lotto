package msa.pseudo.lotto.userserivce.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class JoinRequest {

    @NotNull(message = "ID를 입력해주세요.")
    @Size(min = 4, max = 16, message = "ID의 길이 제한은 4 ~ 16문자 입니다.")
    private String userId;

    @NotNull(message = "패스워드를 입력해주세요.")
    @Size(min = 6, max = 24, message = "패스워드의 길이 제한은 6 ~ 24문자 입니다.")
    private String password;
}
