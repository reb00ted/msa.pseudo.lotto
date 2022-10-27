package msa.pseudo.lotto.userserivce.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@NoArgsConstructor @Getter
public class User extends CreationTimeEntity {
    @Id @GeneratedValue
    private Long id;

    @Column(name = "user_id", unique = true)
    @Size(min = 4, max = 16)
    private String userId;

    @Column(name = "password")
    private String password;

    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;

    public User(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public void updateLastLogin() {
        this.lastLoginAt = LocalDateTime.now();
    }
}
