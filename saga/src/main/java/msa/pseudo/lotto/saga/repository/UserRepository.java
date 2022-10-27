package msa.pseudo.lotto.saga.repository;

import msa.pseudo.lotto.saga.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
