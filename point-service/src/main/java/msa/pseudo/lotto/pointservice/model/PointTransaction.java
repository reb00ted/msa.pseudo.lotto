package msa.pseudo.lotto.pointservice.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import msa.pseudo.lotto.pointservice.dto.PaymentCancelRequest;
import msa.pseudo.lotto.pointservice.dto.PaymentRequest;

import javax.persistence.*;

@Entity
@Table(name = "point_transactions")
@Getter @NoArgsConstructor
public class PointTransaction extends CreationTimeEntity {
    @Id @GeneratedValue
    private Long id;

    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne
    private User user;

    @Column(name = "tx_id", unique = true)
    private Long txId;


    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private TransactionType transactionType;

    @Column(name = "amount", nullable = false)
    private Long amount;

    public PointTransaction(User user, TransactionType type, Long amount) {
        this.user = user;
        this.transactionType = type;
        this.amount = amount;
    }
    public PointTransaction(User user, PaymentRequest request) {
        this.user = user;
        this.txId = request.getTxId();
        this.transactionType = TransactionType.PAYMENT;
        this.amount = -request.getPrice();
    }

    public PointTransaction(User user, PaymentCancelRequest request) {
        this.user = user;
        this.txId = request.getTxId();
        this.transactionType = TransactionType.PAYMENT_CANCELLED;
        this.amount = request.getPrice();
    }
}
