package msa.pseudo.lotto.userserivce.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "outbox")
@Getter @NoArgsConstructor
public class Outbox {
    @Id
    private String topic;

    @Column(name = "last_commit")
    private Long lastCommit;

    public Outbox(String topic, Long lastCommit) {
        this.topic = topic;
        this.lastCommit = lastCommit;
    }

    public void updateLastCommit(Long lastCommit) {
        this.lastCommit = lastCommit;
    }
}
