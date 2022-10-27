package msa.pseudo.lotto.pointservice.model;

import lombok.Getter;
import org.springframework.data.domain.Persistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
public class User extends CreationTimeEntity implements Persistable<String> {

    @Id
    private String userId;

    @Column(name = "point")
    private Long point;

    public User() {

    }
    public User(String userId) {
        this.userId = userId;
        this.point = 0L;
    }

    public Long charge(Long amount) {
        return this.point += amount;
    }

    public Long withdraw(Long amount) {
        if (this.point < amount) {
            throw new IllegalStateException();
        }
        return this.point -= amount;
    }

    public Long payment(Long amount) {
        if (this.point < amount) {
            throw new IllegalStateException();
        }
        return this.point -= amount;
    }

    public Long paymentCancel(Long amount) {
        return this.point += amount;
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
