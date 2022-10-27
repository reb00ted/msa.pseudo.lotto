package msa.pseudo.lotto.lottoservice.repository;

import msa.pseudo.lotto.lottoservice.domain.Outbox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OutboxRepository extends JpaRepository<Outbox, Long> {
    @Query("SELECT o FROM Outbox o WHERE o.processed = false")
    List<Outbox> findToPublishList();
}
