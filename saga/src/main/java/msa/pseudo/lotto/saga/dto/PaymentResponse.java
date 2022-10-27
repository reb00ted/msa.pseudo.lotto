package msa.pseudo.lotto.saga.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PaymentResponse {
    private String txId;
    private String userId;
    private Long price;
    private Long prevPoint;
    private Long currPoint;
    private LocalDateTime createdAt;

}
