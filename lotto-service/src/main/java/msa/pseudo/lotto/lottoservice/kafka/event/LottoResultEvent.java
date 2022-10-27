package msa.pseudo.lotto.lottoservice.kafka.event;

import lombok.Data;
import msa.pseudo.lotto.lottoservice.domain.Outbox;

@Data
public class LottoResultEvent {
    private Long txId;
    private String userId;
    private Long spend;
    private Long profit;

    public LottoResultEvent(Long txId, String userId, Long price, Long winnings) {
        this.txId = txId;
        this.userId = userId;
        this.spend = price;
        this.profit = winnings;
    }

    public LottoResultEvent(Outbox outbox) {
        this.txId = outbox.getTxId();
        this.userId = outbox.getUser().getUserId();
        this.spend = outbox.getPrice();
        this.profit = outbox.getWinnings();
    }
}
