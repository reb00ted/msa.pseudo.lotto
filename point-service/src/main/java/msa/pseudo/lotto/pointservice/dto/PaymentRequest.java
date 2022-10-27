package msa.pseudo.lotto.pointservice.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class PaymentRequest {
    @NotNull
    private Long txId;

    @NotNull
    private String userId;

    @NotNull
    @Positive
    private Long price;
}
