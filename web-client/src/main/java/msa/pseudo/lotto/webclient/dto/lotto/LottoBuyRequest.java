package msa.pseudo.lotto.webclient.dto.lotto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class LottoBuyRequest {
    private Integer lottoRound;
    private Long price;
    private List<Integer> lottoNumbers;
    private LocalDateTime requestAt;
}
