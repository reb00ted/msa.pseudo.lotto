package msa.pseudo.lotto.webclient.dto.lotto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor
public class LottoResponse {
    private Long txId;
    private String userId;
    private Integer round;
    private List<LottoNumberDto> numbers;
    private Integer ranking;
    private Long winnings;
    private LocalDateTime createdAt;
}
