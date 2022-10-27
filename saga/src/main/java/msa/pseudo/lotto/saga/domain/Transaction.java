package msa.pseudo.lotto.saga.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import msa.pseudo.lotto.saga.dto.LottoBuyRequest;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "transactions")
@NoArgsConstructor @Getter
public class Transaction extends CreationTimeEntity implements Persistable<Long> {
    @Id
    private Long txId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TransactionStatus status;

    @Column(name = "price")
    private Long price;
    @Column(name = "lotto_round")
    private Integer lottoRound;

    @Column(name = "num1")
    private Integer num1;
    @Column(name = "num2")
    private Integer num2;
    @Column(name = "num3")
    private Integer num3;
    @Column(name = "num4")
    private Integer num4;
    @Column(name = "num5")
    private Integer num5;
    @Column(name = "num6")
    private Integer num6;

    @Column(name = "request_at")
    private LocalDateTime requestAt;

    @Column(name = "ended_at")
    private LocalDateTime endedAt;


    public Transaction(User user, LottoBuyRequest request) {
        this.txId = request.getTxId();
        this.user = user;
        this.price = request.getPrice();
        this.lottoRound = request.getLottoRound();
        this.status = TransactionStatus.PAYMENT_REQUEST;
        List<Integer> numbers = request.getLottoNumbers();
        this.num1 = numbers.get(0);
        this.num2 = numbers.get(1);
        this.num3 = numbers.get(2);
        this.num4 = numbers.get(3);
        this.num5 = numbers.get(4);
        this.num6 = numbers.get(5);
        this.requestAt = request.getRequestAt();
    }

    public static Transaction paymentRequest() {
        return new Transaction();
    }

    public static Transaction init(User user, long txId, long price, int lottoRound) {
        return new Transaction();
    }

    public void withdrawCompleted() {
        if (this.status != TransactionStatus.PAYMENT_REQUEST) {
            throw new IllegalStateException("출금 요청 상태에서만 출금 완료 상태로 전이될 수 있습니다.");
        }
        this.status = TransactionStatus.PAYMENT_COMPLETED;
    }

    public void completed() {
        if (this.status != TransactionStatus.PAYMENT_COMPLETED) {
            throw new IllegalStateException("출금 완료 상태에서만 완료 상태로 전이될 수 있습니다.");
        }
        this.status = TransactionStatus.COMPLETED;
        this.endedAt = LocalDateTime.now();
    }

    public void failed() {
        this.status = TransactionStatus.FAILED;
        this.endedAt = LocalDateTime.now();
    }

    @Override
    public Long getId() {
        return txId;
    }

    @Override
    public boolean isNew() {
        return getCreatedAt() == null;
    }
}
