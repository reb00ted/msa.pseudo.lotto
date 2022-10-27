package msa.pseudo.lotto.statisticsservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import msa.pseudo.lotto.statisticsservice.domain.User;

@Data @NoArgsConstructor @AllArgsConstructor
public class UserDto {

    private String userId;
    private Long spend;
    private Long profit;
    private Long total;

    public UserDto(User user) {
        this.userId = user.getUserId();
        this.spend = user.getSpend();
        this.profit = user.getProfit();
        this.total = user.getTotal();
    }

}
