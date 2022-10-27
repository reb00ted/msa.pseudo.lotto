package msa.pseudo.lotto.lottoservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import msa.pseudo.lotto.lottoservice.domain.LottoWinnings;

@Data @NoArgsConstructor @AllArgsConstructor
public class LottoWinningsDto {
    private int ranking;
    private Long winnings;

    public LottoWinningsDto(LottoWinnings lottoWinnings) {
        this.ranking = lottoWinnings.getId().getRanking();
        this.winnings = lottoWinnings.getWinnings();
    }
}
