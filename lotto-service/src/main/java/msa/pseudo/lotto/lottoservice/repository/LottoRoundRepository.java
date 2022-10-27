package msa.pseudo.lotto.lottoservice.repository;

import msa.pseudo.lotto.lottoservice.domain.LottoRound;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LottoRoundRepository extends JpaRepository<LottoRound, Integer> {


    LottoRound findTopByOrderByRoundDesc();

    Optional<LottoRound> findByRound(@Param("round") Integer round);

    @Query("SELECT DISTINCT lr FROM LottoRound lr JOIN FETCH lr.winningNumbers WHERE lr.round = :round")
    LottoRound findByRoundWithWinningNumbers(@Param("round") Integer round);

    @Query("SELECT DISTINCT lr FROM LottoRound lr JOIN FETCH lr.winnings WHERE lr.round = :round")
    LottoRound findByRoundWithWinnings(@Param("round") Integer round);
}
