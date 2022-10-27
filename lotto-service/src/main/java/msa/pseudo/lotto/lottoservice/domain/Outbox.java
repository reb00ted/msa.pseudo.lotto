package msa.pseudo.lotto.lottoservice.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;

@Entity
@Table(name = "outbox")
@Getter
@NoArgsConstructor
@ToString
public class Outbox extends CreationTimeEntity implements Persistable<Long> {
    @Id
    private Long txId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "price", nullable = false)
    private Long price;

    @Column(name = "winnings", nullable = false)
    private Long winnings;

    @Column(name = "processed")
    private Boolean processed;

    public Outbox(Long txId, User user, Long price, Long winnings) {
        this.txId = txId;
        this.user = user;
        this.price = price;
        this.winnings = winnings;
        this.processed = false;
    }

    public Outbox(Lotto lotto) {
        this.txId = lotto.getTxId();
        this.user = lotto.getUser();
        this.price = lotto.getPrice();
        this.winnings = lotto.getWinnings();
        this.processed = false;
    }

    public void complete() {
        this.processed = true;
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
