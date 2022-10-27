package msa.pseudo.lotto.webclient.handler;

import lombok.RequiredArgsConstructor;
import lombok.val;
import msa.pseudo.lotto.webclient.dto.point.ChargeRequest;
import msa.pseudo.lotto.webclient.dto.point.ChargeResponse;
import msa.pseudo.lotto.webclient.dto.point.PointHistoryResponse;
import msa.pseudo.lotto.webclient.dto.point.WithdrawRequest;
import msa.pseudo.lotto.webclient.dto.user.JoinRequest;
import msa.pseudo.lotto.webclient.validator.ChargeRequestValidator;
import msa.pseudo.lotto.webclient.validator.WithdrawRequestValidator;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class PointHandler {

    private final WebClient webClient;
    private final ChargeRequestValidator chargeRequestValidator;
    private final WithdrawRequestValidator withdrawRequestValidator;

    public Mono<ServerResponse> chargeForm(ServerRequest request) {
        return ServerResponse.ok().render("point/charge", request.attributes());
    }

    public Mono<ServerResponse> withdrawForm(ServerRequest request) {
        return ServerResponse.ok().render("point/withdraw", request.attributes());
    }

    public Mono<ServerResponse> history(ServerRequest request) {
        String page = request.pathVariable("page");
        String sessionId = request.cookies().getFirst("sessionId").getValue();
        Mono<PointHistoryResponse> pointHistory = webClient.get()
                .uri("/point/history/" + page)
                .cookie("sessionId", sessionId)
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(clientResponse -> {
                    if (clientResponse.statusCode() == HttpStatus.OK) {
                        return clientResponse.bodyToMono(PointHistoryResponse.class);
                    }
                    return Mono.empty();
                });

        Map<String, Object> model = request.attributes();
        model.put("pointHistory", pointHistory);
        return ServerResponse.ok().render("users/pointHistory", model);
    }

    public Mono<ServerResponse> charge(ServerRequest request) {
        String sessionId = request.cookies().getFirst("sessionId").getValue();
        return request.bodyToMono(ChargeRequest.class)
                .doOnNext(this::validate)
                .flatMap(chargeRequest -> {
                    System.out.println(chargeRequest);
                    return webClient.post()
                            .uri("/point/charge")
                            .cookie("sessionId", sessionId)
                            .body(Mono.just(chargeRequest), ChargeRequest.class)
                            .exchangeToMono(clientResponse -> {
                                System.out.println(clientResponse.statusCode());
                                if (clientResponse.statusCode() == HttpStatus.OK) {
                                    return clientResponse.bodyToMono(ChargeResponse.class);
                                }
                                return Mono.just(new ChargeResponse("실패했습니다."));
                            });
                })
                .flatMap(chargeResponse -> ServerResponse.ok().body(Mono.just(chargeResponse), ChargeResponse.class));
    }

    public Mono<ServerResponse> withdraw(ServerRequest request) {
        String sessionId = request.cookies().getFirst("sessionId").getValue();
        return request.bodyToMono(WithdrawRequest.class)
                .doOnNext(this::validate)
                .flatMap(withdrawRequest -> {
                    System.out.println(withdrawRequest);
                    return webClient.post()
                            .uri("/point/withdraw")
                            .cookie("sessionId", sessionId)
                            .body(Mono.just(withdrawRequest), WithdrawRequest.class)
                            .exchangeToMono(clientResponse -> {
                                System.out.println(clientResponse.statusCode());
                                if (clientResponse.statusCode() == HttpStatus.OK) {
                                    return clientResponse.bodyToMono(ChargeResponse.class);
                                }
                                return Mono.just(new ChargeResponse("실패했습니다."));
                            });
                })
                .flatMap(chargeResponse -> ServerResponse.ok().body(Mono.just(chargeResponse), ChargeResponse.class));
    }


    private void validate(ChargeRequest chargeRequest) {
        System.out.println(chargeRequest);
        Errors errors = new BeanPropertyBindingResult(chargeRequest, "chargeRequest");
        chargeRequestValidator.validate(chargeRequest, errors);

        if (errors.hasErrors()) {
            throw new IllegalArgumentException(errors.toString());
        }
    }

    private void validate(WithdrawRequest withdrawRequest) {
        Errors errors = new BeanPropertyBindingResult(withdrawRequest, "withdrawRequest");
        withdrawRequestValidator.validate(withdrawRequest, errors);

        if (errors.hasErrors()) {
            throw new IllegalArgumentException(errors.toString());
        }
    }




}
