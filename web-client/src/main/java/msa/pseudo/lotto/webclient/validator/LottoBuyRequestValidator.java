package msa.pseudo.lotto.webclient.validator;

import msa.pseudo.lotto.webclient.dto.lotto.LottoBuyRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class LottoBuyRequestValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return LottoBuyRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        LottoBuyRequest request = (LottoBuyRequest) target;

        Integer lottoRound = request.getLottoRound();
        if (lottoRound == null || lottoRound < 1) {
            errors.reject("회차 정보가 유효하지 않습니다.");
        }

        Long price = request.getPrice();
        if (price == null || price < 0) {
            errors.reject("가격 정보가 유효하지 않습니다.");
        }

        Set<Integer> lottoNumbers = request.getLottoNumbers().stream().collect(Collectors.toSet());
        if (lottoNumbers.size() > 6) {
            errors.reject("로또 번호가 유효하지 않습니다.");
        }
        Random random = new Random();
        while (lottoNumbers.size() < 6) {
            lottoNumbers.add(random.nextInt(45) + 1);
        }
        request.setLottoNumbers(lottoNumbers.stream().collect(Collectors.toList()));
        request.setRequestAt(LocalDateTime.now());
    }
}
