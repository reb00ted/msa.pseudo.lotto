package msa.pseudo.lotto.lottoservice.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@Table(name = "lotto_winning_numbers")
@NoArgsConstructor
@Getter
public class LottoWinningNumber implements Persistable<LottoWinningNumberId> {

    @EmbeddedId
    private LottoWinningNumberId id;

    public LottoWinningNumber(LottoRound lottoRound, Integer number) {
        this.id = new LottoWinningNumberId(lottoRound, number);
    }

    @Override
    public int hashCode() {
        return (this.id.getLottoRound().getRound().intValue() << 26) | this.id.getNumber().intValue();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof LottoWinningNumber)) {
            return false;
        }
        LottoWinningNumber other = (LottoWinningNumber) obj;
        return this.id.getLottoRound().getRound().longValue() == other.getId().getLottoRound().getRound().longValue()
                && this.id.getNumber().intValue() == other.getId().getNumber().intValue();
    }

    @Override
    public boolean isNew() {
        return true;
    }
}