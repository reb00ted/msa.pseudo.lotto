package msa.pseudo.lotto.statisticsservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import msa.pseudo.lotto.statisticsservice.domain.User;
import msa.pseudo.lotto.statisticsservice.redis.Statistics;

import java.util.List;
import java.util.stream.Collectors;

@Data @NoArgsConstructor
public class StatisticsDto {
    List<UserDto> statistics;

    public StatisticsDto(Statistics statistics) {
        this.statistics = statistics.getData();
    }

}
