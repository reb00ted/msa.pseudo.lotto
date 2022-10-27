package msa.pseudo.lotto.pointservice.kafka.event;

import lombok.Data;

@Data
public class LottoResultEvent {
    private Long txId;
    private String userId;
    private Long spend;
    private Long profit;
}
