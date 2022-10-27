package msa.pseudo.lotto.lottoservice.repository;

import msa.pseudo.lotto.lottoservice.domain.Lotto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LottoRepository extends JpaRepository<Lotto, Long> {

    @Query("SELECT DISTINCT l FROM Lotto l JOIN FETCH l.numbers WHERE l.round.round = :round")
    List<Lotto> findByRound(@Param("round") Integer round);

    Page<Lotto> findByUserUserIdOrderByTxIdDesc(@Param("userId") String userId, Pageable pageable);

    List<Lotto> findByUserUserId(@Param("userId") String userId);
}
