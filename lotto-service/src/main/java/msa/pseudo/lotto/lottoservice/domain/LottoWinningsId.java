package msa.pseudo.lotto.lottoservice.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor
public class LottoWinningsId implements Serializable {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "lotto_round", referencedColumnName = "round")
    private LottoRound lottoRound;

    @Column(name = "ranking")
    private Integer ranking;

    public LottoWinningsId(LottoRound lottoRound, int ranking) {
        this.lottoRound = lottoRound;
        this.ranking = ranking;
    }
}
