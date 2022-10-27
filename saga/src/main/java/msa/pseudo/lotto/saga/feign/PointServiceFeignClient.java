package msa.pseudo.lotto.saga.feign;

import msa.pseudo.lotto.saga.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient("point-service")
public interface PointServiceFeignClient {

    // /point/{userId}/payment
    @PostMapping("/point/payment")
    public PaymentResponse payment(@RequestHeader("userId") String userId,
                                   @RequestBody PaymentRequest request);

    // /point/{userId}/payment/cancel
    @PostMapping("/point/payment/cancel")
    public PaymentCancelResponse paymentCancel(@RequestHeader("userId") String userId,
                                               @RequestBody PaymentCancelRequest request);
}
