package msa.pseudo.lotto.webclient.validator;

import msa.pseudo.lotto.webclient.dto.user.JoinRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class JoinRequestValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return JoinRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        JoinRequest request = (JoinRequest) target;
        String userId, password;
        if ((userId = request.getUserId()) == null || userId.length() < 4 || userId.length() > 16) {
            errors.reject("userId", "ID가 유효하지 않습니다.");
        }
        if ((password = request.getPassword()) == null || password.length() < 4 || password.length() > 16) {
            errors.reject("password", "패스워드가 유효하지 않습니다.");
        }

    }
}
