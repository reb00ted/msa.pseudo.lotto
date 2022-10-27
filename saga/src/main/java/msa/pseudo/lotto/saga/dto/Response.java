package msa.pseudo.lotto.saga.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import msa.pseudo.lotto.saga.domain.Transaction;
import msa.pseudo.lotto.saga.domain.TransactionStatus;

import java.time.LocalDateTime;

@Data @NoArgsConstructor
public class Response {

    private Long txId;
    private String userId;
    private TransactionStatus status;
    private Long price;

    private Integer lottoRound;
    private Integer num1;
    private Integer num2;
    private Integer num3;
    private Integer num4;
    private Integer num5;
    private Integer num6;

    private LocalDateTime requestAt;
    private LocalDateTime endedAt;

    public Response(Transaction transaction) {
        this.txId = transaction.getTxId();
        this.userId = transaction.getUser().getUserId();
        this.status = transaction.getStatus();
        this.price = transaction.getPrice();
        this.lottoRound = transaction.getLottoRound();
        this.num1 = transaction.getNum1();
        this.num2 = transaction.getNum2();
        this.num3 = transaction.getNum3();
        this.num4 = transaction.getNum4();
        this.num5 = transaction.getNum5();
        this.num6 = transaction.getNum6();
        this.requestAt = transaction.getRequestAt();
        this.endedAt = transaction.getEndedAt();
    }
}
