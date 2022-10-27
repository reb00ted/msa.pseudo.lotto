package msa.pseudo.lotto.lottoservice.domain;

import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "lotto")
@Getter
@NoArgsConstructor @Builder @AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class Lotto extends CreationTimeEntity implements Persistable<Long> {

    @Id
    private Long txId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "round")
    private LottoRound round;

    @BatchSize(size = 100)
    @OneToMany(mappedBy = "id.lotto", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH})
    private List<LottoNumber> numbers;

    // 구입 가격
    @Column(name = "price")
    private Long price;

    // 당첨 등수
    @Column(name = "ranking")
    private Integer ranking;

    // 당첨금
    @Column(name = "winnings")
    private Long winnings;

    public Lotto(Long txId, User user, LottoRound round, long price) {
        this.txId = txId;
        this.user = user;
        this.round = round;
        this.price = price;
    }

    public void setLottoNumbers(List<Integer> numbers) {
        this.numbers = numbers.stream()
                .map(i -> new LottoNumber(this, i))
                .collect(Collectors.toList());
    }

    public List<Integer> getNumberListByIntegerList() {
        return this.numbers.stream()
                .map(lottoNumber -> lottoNumber.getId().getNumber())
                .sorted()
                .collect(Collectors.toList());
    }

    public void finish(int ranking, long winnings) {
        this.ranking = ranking;
        this.winnings = winnings;
    }

    @Override
    public Long getId() {
        return txId;
    }

    @Override
    public boolean isNew() {
        return getCreatedAt() == null;
    }
}
