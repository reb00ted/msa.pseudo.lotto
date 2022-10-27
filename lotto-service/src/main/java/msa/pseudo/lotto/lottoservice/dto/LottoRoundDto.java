package msa.pseudo.lotto.lottoservice.dto;

import lombok.Data;
import msa.pseudo.lotto.lottoservice.domain.LottoRound;
import msa.pseudo.lotto.lottoservice.domain.LottoWinningNumber;
import msa.pseudo.lotto.lottoservice.domain.LottoWinnings;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Data
public class LottoRoundDto {
    private Integer round;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private Long price;
    private Integer bonusNumber;
    private Set<Integer> winningNumbers = new HashSet<>();
    private List<Long> winnings = new ArrayList<>();

    public LottoRoundDto(LottoRound lottoRound) {
        this.round = lottoRound.getRound();
        this.startDate = lottoRound.getStartDate();
        this.endDate = lottoRound.getEndDate();
        this.price = lottoRound.getPrice();
        this.bonusNumber = lottoRound.getBonusNumber();
//        this.winningNumbers = lottoRound.getwinningNumbers().stream()
//                .map(lottoWinningNumber -> lottoWinningNumber.getId().getNumber())
//                .collect(Collectors.toSet());
//        this.lottoWinnings = lottoRound.getLottoWinnings().stream()
//                .map(winnings -> winnings.getWinnings())
//                .sorted()
//                .collect(Collectors.toList());
    }

    public void setWinningNumbers(Set<LottoWinningNumber> numbers) {
        this.winningNumbers = numbers.stream()
                .map(lottoWinningNumber -> lottoWinningNumber.getId().getNumber())
                .collect(Collectors.toSet());
    }

    public void setWinnings(List<LottoWinnings> winnings) {
        this.winnings = winnings.stream()
                .map(winnings_ -> winnings_.getWinnings())
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
    }

}
