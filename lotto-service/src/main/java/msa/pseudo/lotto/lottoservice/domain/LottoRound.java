package msa.pseudo.lotto.lottoservice.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

// LottoWinningNumber와 LottoWinnings의 Peresistable.isNew()를 항상 true로 설정했기 때문에
// repository.save() 호출 시 주의해야 함

@Entity
@Table(name = "lotto_round")
@Getter
@NoArgsConstructor
public class LottoRound extends CreationTimeEntity implements Persistable<Integer> {
    @Id @Column(name = "round")
    private Integer round;

    @Column(name = "price")
    private Long price;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "bonus_number")
    private Integer bonusNumber;

    @OneToMany(mappedBy = "id.lottoRound", cascade = CascadeType.MERGE)
    private Set<LottoWinningNumber> winningNumbers = new HashSet<>();

    @OneToMany(mappedBy = "id.lottoRound", cascade = CascadeType.MERGE)
    private List<LottoWinnings> winnings = new ArrayList<>();

//    public List<Integer> getWinningNumberOfIntegerList() {
//        return winningNumbers.stream().map(winningNumber -> winningNumber.getNumber()).collect(Collectors.toList());
//    }

    public LottoRound(int round, LocalDateTime start, LocalDateTime end) {
        this.round = round;
        this.startDate = start;
        this.endDate = end;
        this.price = 1000L;
    }


    public LottoRound(LottoRound roundWithWinningNumbers, LottoRound roundWithWinnings) {
        if (roundWithWinningNumbers.getRound() != roundWithWinnings.getRound()) {
            throw new IllegalStateException("회차 정보가 다름");
        }
        this.round = roundWithWinningNumbers.getRound();
        this.startDate = roundWithWinningNumbers.getStartDate();
        this.endDate = roundWithWinningNumbers.getEndDate();
        this.price = 1000L;
        this.winningNumbers = roundWithWinningNumbers.getWinningNumbers();
        this.winnings = roundWithWinnings.getWinnings();
    }

    public void publishWinningNumbers(Set<Integer> winningNumbers) {
        this.winningNumbers = winningNumbers.stream()
                .map(num -> new LottoWinningNumber(this, num))
                .collect(Collectors.toSet());
    }

    public void publishBonusNumber(int bonusNumber) {
        this.bonusNumber = bonusNumber;
    }

    public void publishWinnings(List<Long> winnings) {
        this.winnings = IntStream.range(0, 6)
                .mapToObj(i -> new LottoWinnings(this, i + 1, winnings.get(i)))
                .collect(Collectors.toList());
    }

    public Set<Integer> getWinningNumbersAsIntegerSet() {
        return winningNumbers.stream().map(num -> num.getId().getNumber()).collect(Collectors.toSet());
    }

    public List<Long> getWinningsAsLongList() {
        return winnings.stream().map(winnings -> winnings.getWinnings())
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
    }

    @Override
    public Integer getId() {
        return round;
    }

    @Override
    public boolean isNew() {
        return getCreatedAt() == null;
    }
}
