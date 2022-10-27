package msa.pseudo.lotto.lottoservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import msa.pseudo.lotto.lottoservice.domain.LottoRound;
import msa.pseudo.lotto.lottoservice.domain.LottoWinningNumber;
import msa.pseudo.lotto.lottoservice.domain.LottoWinnings;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LottoRoundResponse {
    private Integer round;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Long price;
    private Integer bonusNumber;
    private List<Integer> winningNumbers;
    private List<Long> winnings;

    public LottoRoundResponse(LottoRound lottoRound) {
        this.round = lottoRound.getRound();
        this.startDate = lottoRound.getStartDate();
        this.endDate = lottoRound.getEndDate();
        this.price = lottoRound.getPrice();
        this.bonusNumber = lottoRound.getBonusNumber();
    }

    public void setWinningNumbers(Set<LottoWinningNumber> numbers) {
        this.winningNumbers = numbers.stream()
                .map(lottoWinningNumber -> lottoWinningNumber.getId().getNumber())
                .sorted()
                .collect(Collectors.toList());
    }

    public void setWinnings(List<LottoWinnings> winnings) {
        this.winnings = winnings.stream()
                .map(winnings_ -> winnings_.getWinnings())
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
    }
}
