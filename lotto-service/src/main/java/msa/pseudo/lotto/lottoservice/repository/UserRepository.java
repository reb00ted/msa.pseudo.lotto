package msa.pseudo.lotto.lottoservice.repository;

import msa.pseudo.lotto.lottoservice.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
