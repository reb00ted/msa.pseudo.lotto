package msa.pseudo.lotto.lottoservice.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;

@Entity
@Table(name = "lotto_winnings")
@Data
@NoArgsConstructor
public class LottoWinnings implements Persistable<LottoWinningsId> {

    @EmbeddedId
    private LottoWinningsId id;

    @Column(name = "winnings")
    private Long winnings;


    public LottoWinnings(LottoRound round, int ranking, long winnings) {
        this.id = new LottoWinningsId(round, ranking);
        this.winnings = winnings;
    }

    @Override
    public boolean isNew() {
        return true;
    }
}