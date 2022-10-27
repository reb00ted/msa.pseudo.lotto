package msa.pseudo.lotto.webclient.validator;

import msa.pseudo.lotto.webclient.dto.point.WithdrawRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class WithdrawRequestValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return WithdrawRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        WithdrawRequest withdrawRequest = (WithdrawRequest) target;
        Long amount = withdrawRequest.getAmount();
        if (amount < 1) {
            errors.reject("amount", "출금 금액은 1보다 작을 수 없습니다.");
        }

    }
}
