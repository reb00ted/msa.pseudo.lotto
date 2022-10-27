package msa.pseudo.lotto.statisticsservice.repository;

import msa.pseudo.lotto.statisticsservice.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;
import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    User findByUserId(String userId);

    Page<User> findByOrderByTotalDesc(Pageable pageable);
    Page<User> findByOrderByTotalAsc(Pageable pageable);
}
