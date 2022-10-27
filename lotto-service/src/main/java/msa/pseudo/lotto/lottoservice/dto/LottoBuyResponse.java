package msa.pseudo.lotto.lottoservice.dto;

import lombok.Data;
import msa.pseudo.lotto.lottoservice.controller.validator.LottoNumberVerification;
import msa.pseudo.lotto.lottoservice.domain.Lotto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class LottoBuyResponse {

    private Long txId;

    private String userId;

    private Long price;

    private Integer lottoRound;

    private List<Integer> lottoNumbers;

    private LocalDateTime requestAt;

    private LocalDateTime finishedAt;

    public LottoBuyResponse(Lotto lotto) {
        this.txId = lotto.getId();
        this.userId = lotto.getUser().getUserId();
        this.price = lotto.getPrice();
        this.lottoRound = lotto.getRound().getRound();
        this.lottoNumbers = lotto.getNumberListByIntegerList();
        this.finishedAt = lotto.getCreatedAt();
    }
}
