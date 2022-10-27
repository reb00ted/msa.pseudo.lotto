package msa.pseudo.lotto.lottoservice.domain;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class LottoNumberId implements Serializable {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "lotto_id")
    private Lotto lotto;

    @Min(1) @Max(45)
    @Column(name = "number")
    private Integer number;

    public LottoNumberId(Lotto lotto, Integer number) {
        this.lotto = lotto;
        this.number = number;
    }

}
