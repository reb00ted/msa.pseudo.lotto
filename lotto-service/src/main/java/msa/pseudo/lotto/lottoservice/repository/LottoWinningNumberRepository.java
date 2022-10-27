package msa.pseudo.lotto.lottoservice.repository;

import msa.pseudo.lotto.lottoservice.domain.LottoWinningNumber;
import msa.pseudo.lotto.lottoservice.domain.LottoWinningNumberId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LottoWinningNumberRepository extends JpaRepository<LottoWinningNumber, LottoWinningNumberId> {


}
