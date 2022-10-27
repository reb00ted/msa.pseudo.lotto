package msa.pseudo.lotto.webclient.dto.point;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data @NoArgsConstructor @AllArgsConstructor
public class PointTransactionDto {
    private Long id;
    private String userId;
    private Long txId;
    private TransactionType transactionType;
    private Long amount;
    private LocalDateTime createdAt;
}
