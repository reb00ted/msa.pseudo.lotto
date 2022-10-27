package msa.pseudo.lotto.saga.controller;

import feign.RetryableException;
import lombok.RequiredArgsConstructor;
import msa.pseudo.lotto.saga.domain.Transaction;
import msa.pseudo.lotto.saga.dto.*;
import msa.pseudo.lotto.saga.feign.LottoServiceFeignClient;
import msa.pseudo.lotto.saga.feign.PointServiceFeignClient;
import msa.pseudo.lotto.saga.service.Orchestrator;
import msa.pseudo.lotto.saga.service.SagaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.IllegalTransactionStateException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.SocketTimeoutException;

@RestController
@RequiredArgsConstructor
public class SagaController {

    private final SagaService sagaService;
    private final PointServiceFeignClient pointServiceFeignClient;
    private final LottoServiceFeignClient lottoServiceFeignClient;



    @PostMapping("/lotto")
    public ResponseEntity<Response> lottoBuy(
            @RequestHeader(value = "userId", required = false) String userId,
            @RequestHeader("txId") Long txId,
            @RequestBody LottoBuyRequest lottoBuyRequest,
            BindingResult bindingResult)
    {
        System.out.println("/lotto received");
        System.out.println(txId);
        System.out.println(userId);
        System.out.println(lottoBuyRequest);

        if (bindingResult.hasErrors() || userId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        lottoBuyRequest.setUserId(userId);
        lottoBuyRequest.setTxId(txId);
        System.out.println(lottoBuyRequest);
        Response result = sagaService.buy(lottoBuyRequest);
        return ResponseEntity.ok(result);
//        lottoBuyRequest.setUserId(userId);
//        Transaction result = sagaService.buy(lottoBuyRequest);


//        return ResponseEntity.ok().body(null);
    }

    @PostMapping("/test")
    public ResponseEntity test(
            @RequestHeader(value = "userId", required = false) String userId,
            @RequestBody PaymentRequest request,
            BindingResult bindingResult
    ) {

        Orchestrator orchestrator = new Orchestrator(pointServiceFeignClient);
        PaymentResponse paymentResponse;
        try {
//            paymentResponse = pointServiceFeignClient.payment(userId, request);
            orchestrator.payment();
        } catch (RetryableException e) { // SocketTimeoutException
            System.out.println("SocketTimeoutException");
//            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("IOException");
            throw new RuntimeException(e);
        }

//        lottoServiceFeignClient.buy(userId)
        return ResponseEntity.ok().build();
    }

    @PostMapping("/test2")
    public ResponseEntity test2(
            @RequestHeader(value = "userId", required = false) String userId,
            @RequestBody LottoBuyRequest request,
            BindingResult bindingResult
    ) {

        request.setUserId(userId);
        System.out.println(request);
//        LottoBuyResponse response = lottoServiceFeignClient.buy(userId, request);

        return ResponseEntity.ok().build();
    }
}
