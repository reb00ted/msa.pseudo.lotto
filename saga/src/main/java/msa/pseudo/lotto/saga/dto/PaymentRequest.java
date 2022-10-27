package msa.pseudo.lotto.saga.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class PaymentRequest {
    private Long txId;
    private String userId;
    private Long price;

    public PaymentRequest(LottoBuyRequest request) {
        this.txId = request.getTxId();
        this.userId = request.getUserId();
        this.price = request.getPrice();
    }
}
