package msa.pseudo.lotto.saga.service;

import lombok.RequiredArgsConstructor;
import msa.pseudo.lotto.saga.domain.User;
import msa.pseudo.lotto.saga.dto.*;
import msa.pseudo.lotto.saga.domain.Transaction;
import msa.pseudo.lotto.saga.feign.LottoServiceFeignClient;
import msa.pseudo.lotto.saga.feign.PointServiceFeignClient;
import msa.pseudo.lotto.saga.repository.TransactionRepository;
import msa.pseudo.lotto.saga.repository.UserRepository;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SagaService {

    private static final String TX_ID = "tx_id";

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final PointServiceFeignClient pointServiceFeignClient;
    private final LottoServiceFeignClient lottoServiceFeignClient;

    public Response buy(LottoBuyRequest request) {
        // 유저 정보 체크
        String userId = request.getUserId();
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException());

//        Orchestrator orchestrator = new Orchestrator(user, request, pointServiceFeignClient, lottoServiceFeignClient);
//        Transaction transaction = orchestrator.init();
        Transaction transaction = new Transaction(user, request);
        transaction = init(transaction);

//        orchestrator.payment();
        // 포인트 출금 요청
        PaymentResponse paymentResponse = pointServiceFeignClient.payment(userId, makePaymentRequest(request));
        transaction = withdrawCompleted(transaction);

        // 복권 구매 요청
        LottoBuyResponse lottoBuyResponse = lottoServiceFeignClient.buy(request);
        transaction = completed(transaction);
        return new Response(transaction);
//        return new Response();
    }

    // 처음 트랜잭션 시작
    @Transactional
    public Transaction init(Transaction transaction) {
        return transactionRepository.save(transaction);
    }
    
    // 포인트 사용 완료 상태
    @Transactional
    public Transaction withdrawCompleted(Transaction transaction) {
        transaction.withdrawCompleted();
        return transactionRepository.save(transaction);
    }
    
    // 복권 구매 완료 상태(트랜잭션 완료)
    @Transactional
    public Transaction completed(Transaction transaction) {
        transaction.completed();
        return transactionRepository.save(transaction);
    }




    private PaymentRequest makePaymentRequest(LottoBuyRequest request) {
        PaymentRequest result = new PaymentRequest();
        result.setTxId(request.getTxId());
        result.setUserId(request.getUserId());
        result.setPrice(request.getPrice());
        return result;
    }


}
