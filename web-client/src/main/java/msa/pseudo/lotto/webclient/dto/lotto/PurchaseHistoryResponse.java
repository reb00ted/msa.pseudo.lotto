package msa.pseudo.lotto.webclient.dto.lotto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PurchaseHistoryResponse {
    private String txId;
    private Integer round;
    private LocalDateTime createdAt;
    private List<LottoNumbers> numbers;
    private Integer ranking;
    private Long winnings;

}
