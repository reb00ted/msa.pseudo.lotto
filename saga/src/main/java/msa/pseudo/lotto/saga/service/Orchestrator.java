package msa.pseudo.lotto.saga.service;

import msa.pseudo.lotto.saga.domain.Transaction;
import msa.pseudo.lotto.saga.domain.TransactionStatus;
import msa.pseudo.lotto.saga.domain.User;
import msa.pseudo.lotto.saga.dto.LottoBuyRequest;
import msa.pseudo.lotto.saga.dto.LottoBuyResponse;
import msa.pseudo.lotto.saga.dto.PaymentRequest;
import msa.pseudo.lotto.saga.dto.PaymentResponse;
import msa.pseudo.lotto.saga.feign.LottoServiceFeignClient;
import msa.pseudo.lotto.saga.feign.PointServiceFeignClient;
import msa.pseudo.lotto.saga.repository.TransactionRepository;
import org.springframework.transaction.IllegalTransactionStateException;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.time.LocalDateTime;
import java.util.List;

public class Orchestrator {

    private User user;
    private LottoBuyRequest lottoBuyRequest;
    private TransactionStatus status;
    private LottoServiceFeignClient lottoServiceFeignClient;
    private PointServiceFeignClient pointServiceFeignClient;

    public Orchestrator(
            User user,
            LottoBuyRequest request,
            PointServiceFeignClient pointServiceFeignClient,
            LottoServiceFeignClient lottoServiceFeignClient
    ) {
        this.user = user;
        this.lottoBuyRequest = request;
        this.status = TransactionStatus.PAYMENT_REQUEST;
        this.pointServiceFeignClient = pointServiceFeignClient;
        this.lottoServiceFeignClient = lottoServiceFeignClient;
    }

    public Orchestrator(PointServiceFeignClient pointServiceFeignClient) {
        user = new User("hello");
        lottoBuyRequest = new LottoBuyRequest();
        lottoBuyRequest.setTxId(1L);
        lottoBuyRequest.setLottoNumbers(List.of(1, 2, 3, 4, 5, 6));
        lottoBuyRequest.setUserId("hello");
        lottoBuyRequest.setPrice(1000L);
        lottoBuyRequest.setRequestAt(LocalDateTime.now());
        lottoBuyRequest.setLottoRound(3);
        this.pointServiceFeignClient = pointServiceFeignClient;
    }

    public Transaction init() {
        return new Transaction(user, lottoBuyRequest);
    }

//    public Transaction process() {
//        Transaction transaction = new Transaction(user, lottoBuyRequest);
//        transaction = init(transaction);
//
//         포인트 출금 요청
//        PaymentResponse paymentResponse = pointServiceFeignClient.payment(userId, makePaymentRequest(request));
//        transaction = withdrawCompleted(transaction);
//
//         복권 구매 요청
//        LottoBuyResponse lottoBuyResponse = lottoServiceFeignClient.buy(request);
//        transaction = completed(transaction);
//    }

    public void payment() throws IOException {
        try {
            PaymentResponse payment = pointServiceFeignClient.payment(user.getUserId(), new PaymentRequest(lottoBuyRequest));
            // 포인트 결제 성공했지만 성공 응답이 오지 않는 경우라면??
            // 복권 구입이 성공했지만 성공 응답이 오지 않는다면?? 이때 결제를 취소해버린다면??
        } catch (IllegalTransactionStateException e) {
            System.out.println("404 처리");
        }
    }

    public void rollback() {

    }


}
