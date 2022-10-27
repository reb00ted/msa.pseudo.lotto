package msa.pseudo.lotto.statisticsservice.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Getter @NoArgsConstructor
public class User extends CreationTimeEntity implements Persistable<String> {

    @Id
    private String userId;

    @Column(name = "spend")
    private Long spend;

    @Column(name = "profit")
    private Long profit;

    @Column(name = "total")
    private Long total;

    public User(String userId) {
        this.userId = userId;
        this.spend = 0L;
        this.profit = 0L;
        this.total = 0L;
    }


    public void spend(long amount) {
        this.spend += amount;
        this.total -= amount;
    }

    public void profit(long amount) {
        this.profit += amount;
        this.total += amount;
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
