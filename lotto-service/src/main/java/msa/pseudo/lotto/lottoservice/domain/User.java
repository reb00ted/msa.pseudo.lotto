package msa.pseudo.lotto.lottoservice.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;

@Entity
@Table(name = "users")
@NoArgsConstructor @Getter
public class User extends CreationTimeEntity implements Persistable<String> {
    @Id @Column(name = "user_id")
    private String userId;

    public User(String userId) {
        this.userId = userId;
    }

    @Override
    public String getId() {
        return userId;
    }

    @Override
    public boolean isNew() {
        return getCreatedAt() == null;
    }
}
