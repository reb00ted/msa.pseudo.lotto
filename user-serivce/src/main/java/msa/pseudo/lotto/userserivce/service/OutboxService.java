package msa.pseudo.lotto.userserivce.service;

import lombok.RequiredArgsConstructor;
import msa.pseudo.lotto.userserivce.dto.OutboxDto;
import msa.pseudo.lotto.userserivce.model.Outbox;
import msa.pseudo.lotto.userserivce.repository.OutboxRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class OutboxService {

    private final OutboxRepository outboxRepository;

    public Long getLastCommitIndex(String topic) {
        Optional<Outbox> optionalOutbox = outboxRepository.findById(topic);
        if (optionalOutbox.isEmpty()) {
            return -1L;
        }
        return optionalOutbox.get().getLastCommit();
    }

    public OutboxDto createOutbox(String topic) {
        Outbox outbox = new Outbox(topic, 0L);
        outbox = outboxRepository.save(outbox);
        return new OutboxDto(outbox);
    }

    public void updateLastCommitIndex(String topic, long index) {
        Outbox outbox = outboxRepository.findById(topic).orElseThrow(() -> new IllegalArgumentException());
        if (outbox.getLastCommit() >= index) {
            // 현재 인덱스보다 작은 인덱스 값은 갱신하지 않음
            return;
        }
        outbox.updateLastCommit(index);
    }
}
