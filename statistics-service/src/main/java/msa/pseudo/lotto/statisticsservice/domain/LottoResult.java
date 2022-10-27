package msa.pseudo.lotto.statisticsservice.domain;

import msa.pseudo.lotto.statisticsservice.kafka.event.LottoResultEvent;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;

@Entity
@Table(name = "lotto_result")
public class LottoResult extends CreationTimeEntity implements Persistable<Long> {

    @Id
    private Long txId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "spend")
    private Long spend;

    @Column(name = "profit")
    private Long profit;

    public LottoResult(User user, LottoResultEvent lottoResultEvent) {
        this.txId = lottoResultEvent.getTxId();
        this.user = user;
        this.spend = lottoResultEvent.getSpend();
        this.profit = lottoResultEvent.getProfit();
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
