package msa.pseudo.lotto.statisticsservice.redis;

import lombok.Getter;
import lombok.NoArgsConstructor;
import msa.pseudo.lotto.statisticsservice.dto.UserDto;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;
import java.util.List;

@RedisHash("statistics")
@Getter @NoArgsConstructor
public class Statistics {
    @Id
    private String id;

    List<UserDto> data;

    public Statistics(String id, List<UserDto> data) {
        this.id = id;
        this.data = data;
    }
}
