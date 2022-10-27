package msa.pseudo.lotto.statisticsservice.repository;

import msa.pseudo.lotto.statisticsservice.domain.LottoResult;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LottoResultRepository extends JpaRepository<LottoResult, Long> {

}
