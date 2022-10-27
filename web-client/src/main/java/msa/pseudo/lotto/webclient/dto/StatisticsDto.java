package msa.pseudo.lotto.webclient.dto;

import lombok.Data;

@Data
public class StatisticsDto {

    private String loginId;
    private String spend;
    private String earn;
    private String netProfit;

    public StatisticsDto(String loginId, String spend, String earn, String netProfit) {
        this.loginId = loginId;
        this.spend = spend;
        this.earn = earn;
        this.netProfit = netProfit;
    }
}
