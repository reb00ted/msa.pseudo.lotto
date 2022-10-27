package msa.pseudo.lotto.userserivce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import msa.pseudo.lotto.userserivce.model.Outbox;

@Data @NoArgsConstructor @AllArgsConstructor
public class OutboxDto {

    private String topic;
    private Long lastCommit;

    public OutboxDto(Outbox outbox) {
        this.topic = outbox.getTopic();
        this.lastCommit = outbox.getLastCommit();
    }

}
