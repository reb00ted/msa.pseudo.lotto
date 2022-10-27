package msa.pseudo.lotto.pointservice.dto;

import lombok.Data;

@Data
public class PaymentCancelRequest {
    private Long txId;
    private String userId;
    private Long price;
}
