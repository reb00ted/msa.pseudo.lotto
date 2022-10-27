package msa.pseudo.lotto.userserivce.kafka;

import lombok.RequiredArgsConstructor;
import msa.pseudo.lotto.userserivce.common.Utils;
import msa.pseudo.lotto.userserivce.dto.UserDto;
import msa.pseudo.lotto.userserivce.service.OutboxService;
import msa.pseudo.lotto.userserivce.service.UserService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class KafkaProducerScheduler {

    private final OutboxService outboxService;
    private final UserService userService;
    private final KafkaProducer kafkaProducer;

    @Scheduled(cron = "* * * * * *") // 매 초 실행
    public void publishUserCreationEvent() {
        // outbox service 에 메서드 하나로 간단하게 묶자
        Long lastCommit = outboxService.getLastCommitIndex(Utils.USER_CREATION_TOPIC);
        if (lastCommit == -1) {
            outboxService.createOutbox(Utils.USER_CREATION_TOPIC);
            lastCommit = 0L;
        }

        List<UserDto> todoList = userService.findByIdGreaterThan(lastCommit);
        for (UserDto user : todoList) {
            System.out.println(user.toString());
            kafkaProducer.sendMessage(new UserCreationEvent(user.getUserId()));
            lastCommit = Math.max(lastCommit, user.getId());
        }
        outboxService.updateLastCommitIndex(Utils.USER_CREATION_TOPIC, lastCommit);
    }
}
