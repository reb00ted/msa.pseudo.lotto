package msa.pseudo.lotto.lottoservice.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "lotto_numbers")
@Getter
@NoArgsConstructor
public class LottoNumber implements Persistable<LottoNumberId> {

    @EmbeddedId
    private LottoNumberId id;

    @Column(name = "matched")
    private Boolean matched;

    public LottoNumber(Lotto lotto, Integer number) {
        this.id = new LottoNumberId(lotto, number);
    }

    public void match() {
        matched = true;
    }

    public void notMatched() {
        matched = false;
    }

    @Override
    public boolean isNew() {
        return matched == null;
    }

    @Override
    public int hashCode() {
        return id.getLotto().getRound().getRound().intValue() ^ id.getNumber().intValue();
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof LottoNumber)) {
            return false;
        }
        LottoNumber target = (LottoNumber) other;
        return (this.id.getLotto().getRound().getRound().intValue() == target.getId().getLotto().getRound().getRound().intValue())
                && this.id.getNumber().intValue() == target.getId().getNumber().intValue();
    }
}



