package msa.pseudo.lotto.saga.feign;

import msa.pseudo.lotto.saga.dto.LottoBuyRequest;
import msa.pseudo.lotto.saga.dto.LottoBuyResponse;
import msa.pseudo.lotto.saga.dto.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient("lotto-service")
public interface LottoServiceFeignClient {

    // @PostMapping("/lotto/buy")
    @PostMapping("/lotto")
    public LottoBuyResponse buy(@RequestBody LottoBuyRequest lottoBuyRequest);


}
