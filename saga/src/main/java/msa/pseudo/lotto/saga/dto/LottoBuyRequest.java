package msa.pseudo.lotto.saga.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import msa.pseudo.lotto.saga.controller.validator.LottoNumberVerification;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Data @NoArgsConstructor
public class LottoBuyRequest {

    @NotNull
    private Long txId;

    private String userId;

    @NotNull
    @Positive
    private Long price;

    @NotNull
    @Positive
    private Integer lottoRound;

    @NotNull
    @LottoNumberVerification
    private List<Integer> lottoNumbers;

    @PastOrPresent
    private LocalDateTime requestAt;
}
