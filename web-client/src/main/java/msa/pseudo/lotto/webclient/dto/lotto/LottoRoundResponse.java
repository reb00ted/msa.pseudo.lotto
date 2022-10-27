package msa.pseudo.lotto.webclient.dto.lotto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor
public class LottoRoundResponse {
    private Integer round;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Long price;
    private Integer bonusNumber;
    private List<Integer> winningNumbers;
    private List<Long> winnings;
}
