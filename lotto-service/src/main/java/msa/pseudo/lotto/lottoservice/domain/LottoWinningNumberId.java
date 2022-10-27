package msa.pseudo.lotto.lottoservice.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor
public class LottoWinningNumberId implements Serializable {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "lotto_round", referencedColumnName = "round")
    private LottoRound lottoRound;

    @Min(1) @Max(45)
    @Column(name = "number")
    private Integer number;

    public LottoWinningNumberId(LottoRound lottoRound, int number) {
        this.lottoRound = lottoRound;
        this.number = number;
    }
}
