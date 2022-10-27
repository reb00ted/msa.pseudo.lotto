package msa.pseudo.lotto.statisticsservice.kafka.event;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class LottoResultEvent {
    private Long txId;
    private String userId;
    private Long spend;
    private Long profit;
}
