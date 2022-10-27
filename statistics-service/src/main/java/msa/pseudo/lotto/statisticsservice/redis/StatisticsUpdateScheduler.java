package msa.pseudo.lotto.statisticsservice.redis;

import lombok.RequiredArgsConstructor;
import msa.pseudo.lotto.statisticsservice.domain.User;
import msa.pseudo.lotto.statisticsservice.repository.UserRepository;
import msa.pseudo.lotto.statisticsservice.service.StatisticsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StatisticsUpdateScheduler {

    private final StatisticsService statisticsService;

    @Scheduled(cron = "0 * * * * *")
    public void updateStatistics() {
        statisticsService.updateLucky();
        statisticsService.updateUnlucky();
    }
}
