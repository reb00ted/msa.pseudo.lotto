package msa.pseudo.lotto.pointservice.repository;

import msa.pseudo.lotto.pointservice.model.PointTransaction;
import msa.pseudo.lotto.pointservice.model.TransactionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface PointTransactionRepository extends JpaRepository<PointTransaction, Long> {
    PointTransaction findByTxId(@Param("txId") Long txId);

    PointTransaction findByTxIdAndTransactionType(@Param("txId") Long txId, @Param("transactionType") TransactionType transactionType);

    Page<PointTransaction> findByUserUserIdOrderByIdDesc(@Param("userId") String userId, Pageable pageable);
}
