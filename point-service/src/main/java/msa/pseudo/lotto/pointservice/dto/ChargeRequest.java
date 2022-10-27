package msa.pseudo.lotto.pointservice.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ChargeRequest {
    @Min(value = 1000, message = "최소 충전 금액은 1,000원 입니다.")
    @NotNull
    private Long amount;
}
