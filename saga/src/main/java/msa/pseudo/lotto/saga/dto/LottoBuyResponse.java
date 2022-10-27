package msa.pseudo.lotto.saga.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import msa.pseudo.lotto.saga.domain.Transaction;

import java.time.LocalDateTime;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor
public class LottoBuyResponse {
    private Long txId;
    private String userId;
    private Long price;
    private Integer lottoRound;
    private List<Integer> lottoNumbers;
    private LocalDateTime requestAt;
    private LocalDateTime endedAt;

    public LottoBuyResponse(Transaction transaction) {
        this.txId = transaction.getId();
        this.userId = transaction.getUser().getId();
        this.price = transaction.getPrice();
        this.lottoRound = transaction.getLottoRound();
        this.lottoNumbers = List.of(transaction.getNum1(), transaction.getNum2(), transaction.getNum3(),
                transaction.getNum4(), transaction.getNum5(), transaction.getNum6());
        this.requestAt = transaction.getRequestAt();
        this.endedAt = transaction.getEndedAt();
    }
}
