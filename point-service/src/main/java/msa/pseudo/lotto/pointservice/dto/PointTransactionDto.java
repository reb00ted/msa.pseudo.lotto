package msa.pseudo.lotto.pointservice.dto;

import lombok.Data;
import msa.pseudo.lotto.pointservice.model.PointTransaction;
import msa.pseudo.lotto.pointservice.model.TransactionType;

import java.time.LocalDateTime;

@Data
public class PointTransactionDto {
    private Long id;
    private String userId;
    private Long txId;
    private TransactionType transactionType;
    private Long amount;
    private LocalDateTime createdAt;

    public PointTransactionDto(PointTransaction pointTransaction) {
        this.id = pointTransaction.getId();
        this.userId = pointTransaction.getUser().getId();
        this.txId = pointTransaction.getTxId();
        this.transactionType = pointTransaction.getTransactionType();
        this.amount = pointTransaction.getAmount();
        this.createdAt = pointTransaction.getCreatedAt();
    }

}
