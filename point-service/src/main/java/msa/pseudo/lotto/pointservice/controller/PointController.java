package msa.pseudo.lotto.pointservice.controller;

import lombok.RequiredArgsConstructor;
import msa.pseudo.lotto.pointservice.dto.*;
import msa.pseudo.lotto.pointservice.service.PointService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/point")
@RequiredArgsConstructor
public class PointController {

    private final PointService pointService;

    @GetMapping("/{userId}")
    public ResponseEntity<CurrentPoint> getCurrentPoint(
            @PathVariable("userId") String userId
    ) {
        System.out.println("/point/" + userId + " 호출됨");
        System.out.println(userId);
        Long result = pointService.getCurrentPoint(userId);
        System.out.println(result);
        return ResponseEntity.ok(new CurrentPoint(userId, result));
    }

    @PostMapping("/charge")
    public ResponseEntity<ChargeResponse> charge(
            @RequestHeader(value = "userId", required = false) String userId,
            @RequestBody @Validated ChargeRequest chargeRequest,
            BindingResult bindingResult)
    {
        System.out.println("/point/charge 호출됨");
        System.out.printf("%s %S\n", userId, chargeRequest);
        System.out.println(userId);

        if (userId == null || bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        ChargeResponse result = pointService.charge(userId, chargeRequest.getAmount());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<WithdrawResponse> withdraw(
            @RequestHeader(value = "userId", required = false) String userId,
            @RequestBody @Validated WithdrawRequest withdrawRequest,
            BindingResult bindingResult
    ) {
        System.out.printf("/point/withdraw 호출");
        System.out.println(userId);
        System.out.println(withdrawRequest);
        if (userId == null || bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        WithdrawResponse result = pointService.withdraw(userId, withdrawRequest.getAmount());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/payment")
    public ResponseEntity<PaymentResponse> payment(
            @RequestBody @Validated PaymentRequest paymentRequest,
            BindingResult bindingResult)
    {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        System.out.println("/point/payment 확인");
        System.out.println(paymentRequest);
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

//        PaymentResponse result = pointService.payment(paymentRequest);
//        return ResponseEntity.ok().body(result);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/payment/cancel")
    public ResponseEntity<PaymentCancelResponse> paymentCancel(
            @RequestBody @Validated PaymentCancelRequest paymentCancelRequest,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        System.out.println("/point/paymentcancel 확인");
        System.out.println(paymentCancelRequest);
        pointService.paymentCancel(paymentCancelRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/history/{page}")
    public ResponseEntity<PointHistoryResponse> getPointHistory(
            @RequestHeader(value = "userId", required = false) String userId,
            @PathVariable(value = "page", required = false) Integer page
    ) {
        if (userId == null || page == null || page < 1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.ok(pointService.getPointHistory(userId, PageRequest.of(page - 1, 10)));
    }
}
