package msa.pseudo.lotto.userserivce.repository;

import msa.pseudo.lotto.userserivce.model.Outbox;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OutboxRepository extends JpaRepository<Outbox, String> {


}
