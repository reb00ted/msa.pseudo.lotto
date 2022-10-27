package msa.pseudo.lotto.lottoservice.dto;

import lombok.Data;
import msa.pseudo.lotto.lottoservice.domain.Lotto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class LottoResponse {
    private Long txId;
    private String userId;
    private Integer round;
    private List<LottoNumberDto> numbers;
    private Integer ranking;
    private Long winnings;
    private LocalDateTime createdAt;

    public LottoResponse(Lotto lotto) {
        this.txId = lotto.getTxId();
        this.userId = lotto.getUser().getId();
        this.round = lotto.getRound().getRound();
        this.numbers = lotto.getNumbers().stream().map(LottoNumberDto::new).collect(Collectors.toList());
        this.ranking = lotto.getRanking();
        this.winnings = lotto.getWinnings();
        this.createdAt = lotto.getCreatedAt();
    }

}
