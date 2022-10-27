package msa.pseudo.lotto.saga.repository;

import msa.pseudo.lotto.saga.domain.Transaction;
import msa.pseudo.lotto.saga.domain.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT t FROM Transaction t WHERE t.status IN :statuses")
    List<Transaction> findByStatuses(@Param("statuses") List<TransactionStatus> statuses);

    default List<Transaction> findIncompletes() {
        return findByStatuses(List.of(TransactionStatus.PAYMENT_REQUEST, TransactionStatus.PAYMENT_COMPLETED));
    }
}
