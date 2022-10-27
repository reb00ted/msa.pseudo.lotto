package msa.pseudo.lotto.saga.controller.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Comparator;
import java.util.List;

public class LottoNumberVerifier implements ConstraintValidator<LottoNumberVerification, List<Integer>> {
    @Override
    public void initialize(LottoNumberVerification constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(List<Integer> numbers, ConstraintValidatorContext context) {
        if (numbers.size() != 6) return false;
        numbers.sort(Comparator.naturalOrder());
        for (int i = 0; i < 6; i++) {
            if (numbers.get(i) < 1 || numbers.get(i) > 45) return false;
            if (i > 1 && numbers.get(i) == numbers.get(i - 1)) return false;
        }
        return true;
    }
}
