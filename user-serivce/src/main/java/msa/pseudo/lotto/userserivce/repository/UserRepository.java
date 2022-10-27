package msa.pseudo.lotto.userserivce.repository;

import msa.pseudo.lotto.userserivce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserIdAndPassword(@Param("userId") String userId, @Param("password") String password);

    List<User> findByIdGreaterThan(@Param("id") Long id);
}
