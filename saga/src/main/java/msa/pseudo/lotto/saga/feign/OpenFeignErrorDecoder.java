package msa.pseudo.lotto.saga.feign;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.IllegalTransactionStateException;

@Component
public class OpenFeignErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        System.out.println(methodKey);
        System.out.println(response.status());
        System.out.println(response.reason());
        System.out.println(response.request().toString());
        switch (response.status()) {
            case 404:
                return new IllegalTransactionStateException("");
        }
        return new Exception();
    }
}
