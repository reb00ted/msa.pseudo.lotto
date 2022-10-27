package msa.pseudo.lotto.pointservice.dto;

import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class WithdrawRequest {
    @Min(value = 1, message = "출금하려는 금액은 1원 이상이어야 합니다.")
    private Long amount;
}
