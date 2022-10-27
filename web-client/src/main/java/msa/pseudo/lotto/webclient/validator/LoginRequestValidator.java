package msa.pseudo.lotto.webclient.validator;

import msa.pseudo.lotto.webclient.dto.user.LoginRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class LoginRequestValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return LoginRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        LoginRequest request = (LoginRequest) target;
        String userId, password;
        if ((userId = request.getUserId()) == null || userId.length() < 4 || userId.length() > 16) {
            errors.reject("userId", "ID의 길이는 4~16글자입니다.");
        }
        if ((password = request.getPassword()) == null || password.length() < 4 || password.length() > 16) {
            errors.reject("password", "패스워드의 길이는 4~24글자입니다.");
        }

    }
}
