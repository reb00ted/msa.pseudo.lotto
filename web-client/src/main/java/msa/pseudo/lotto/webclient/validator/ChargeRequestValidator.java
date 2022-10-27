package msa.pseudo.lotto.webclient.validator;

import msa.pseudo.lotto.webclient.dto.point.ChargeRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ChargeRequestValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return ChargeRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ChargeRequest chargeRequest = (ChargeRequest) target;
        long amount = chargeRequest.getAmount();
        if (amount < 1000 || amount % 1000 != 0 || amount > 1_000_000) {
            errors.reject("amount", "유효한 충전 금액이 아닙니다.");
        }
    }
}
