package msa.pseudo.lotto.webclient.handler;

import lombok.RequiredArgsConstructor;
import msa.pseudo.lotto.webclient.dto.lotto.LottoBuyRequest;
import msa.pseudo.lotto.webclient.dto.lotto.LottoBuyResponse;
import msa.pseudo.lotto.webclient.dto.lotto.LottoListResponse;
import msa.pseudo.lotto.webclient.dto.lotto.LottoRoundResponse;
import msa.pseudo.lotto.webclient.validator.LottoBuyRequestValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class LottoHandler {

    private final WebClient webClient;
    private final LottoBuyRequestValidator lottoBuyRequestValidator;

    public Mono<ServerResponse> buyForm(ServerRequest request) {
        Mono<LottoRoundResponse> lottoRound = webClient.get()
                .uri("/lottoRound/current")
                .exchangeToMono(clientResponse -> {
                    if (clientResponse.statusCode() == HttpStatus.OK) {
                        return clientResponse.bodyToMono(LottoRoundResponse.class);
                    }
                    return Mono.empty();
                })
                .doOnNext(lottoRoundResponse -> System.out.println(lottoRoundResponse));

        Map<String, Object> model = request.attributes();
        model.put("lottoRound", lottoRound);
        return ServerResponse.ok().render("lotto/buy", model);
    }

    public Mono<ServerResponse> roundForm(ServerRequest request) {
        String round = request.pathVariable("round");
        String uri = "/lottoRound/" + round;
        Mono<LottoRoundResponse> lottoRound = webClient.get()
                .uri(uri)
                .exchangeToMono(clientResponse -> {
                    if (clientResponse.statusCode() == HttpStatus.OK) {
                        return clientResponse.bodyToMono(LottoRoundResponse.class);
                    }
                    return Mono.just(new LottoRoundResponse());
                })
                .doOnNext(lottoRoundResponse -> System.out.println(lottoRoundResponse));
        Mono<List<Integer>> roundList = webClient.get()
                .uri("/lottoRound/last")
                .exchangeToMono(clientResponse -> {
                    if (clientResponse.statusCode() == HttpStatus.OK) {
                        return clientResponse.bodyToMono(String.class);
                    }
                    return Mono.just("0");
                })
                .doOnNext(i -> System.out.println(i))
                .map(last -> IntStream.range(1, Integer.parseInt(last) + 1).boxed().collect(Collectors.toList()));

        Map<String, Object> model = request.attributes();
        model.put("lottoRound", lottoRound);
        model.put("roundList", roundList);
        return ServerResponse.ok().render("lotto/round", model);
    }

    public Mono<ServerResponse> lottoRoundForm(ServerRequest request) {
        String round = request.pathVariable("round");
        String uri = "/lottoRound/" + round;
        Mono<LottoRoundResponse> lottoRound = webClient.get()
                .uri(uri)
                .exchangeToMono(clientResponse -> {
                    if (clientResponse.statusCode() == HttpStatus.OK) {
                        return clientResponse.bodyToMono(LottoRoundResponse.class);
                    }
                    return Mono.just(new LottoRoundResponse());
                })
                .doOnNext(lottoRoundResponse -> System.out.println(lottoRoundResponse));
        Mono<List<Integer>> roundList = webClient.get()
                .uri("/lottoRound/last")
                .exchangeToMono(clientResponse -> {
                    if (clientResponse.statusCode() == HttpStatus.OK) {
                        return clientResponse.bodyToMono(String.class);
                    }
                    return Mono.just("0");
                })
                .doOnNext(i -> System.out.println(i))
                .map(last -> IntStream.range(1, Integer.parseInt(last) + 1).boxed().collect(Collectors.toList()));

        Map<String, Object> model = request.attributes();
        model.put("lottoRound", lottoRound);
        model.put("roundList", roundList);
        return ServerResponse.ok().render("lotto/round", model);
    }

    public Mono<ServerResponse> buy(ServerRequest request) {
        System.out.println("buy start");
        Map<String, Object> attributes = request.attributes();
        String sessionId = request.cookies().getFirst("sessionId").getValue();
        Mono<LottoBuyResponse> lottoBuyRequestMono = request.bodyToMono(LottoBuyRequest.class)
                .doOnNext(this::validate)
                .flatMap(lottoBuyRequest -> {
                    return webClient.post()
                            .uri("/lotto")
                            .cookie("sessionId", sessionId)
                            .body(Mono.just(lottoBuyRequest), LottoBuyRequest.class)
                            .accept(MediaType.APPLICATION_JSON)
                            .exchangeToMono(clientResponse -> {
                                System.out.println(clientResponse.statusCode());
                                if (clientResponse.statusCode() == HttpStatus.OK) {
                                    return clientResponse.bodyToMono(LottoBuyResponse.class);
                                }
                                return Mono.just(new LottoBuyResponse());
                            });
                })
                .map(lottoBuyRequest -> lottoBuyRequest)
                .doOnSubscribe(System.out::println);

        return ServerResponse.ok().body(lottoBuyRequestMono, LottoBuyResponse.class);
    }

    public Mono<ServerResponse> lottoList(ServerRequest request) {
        String page = request.pathVariable("page");
        String uri = "/lotto/list/" + page;
        String sessionId = request.cookies().getFirst("sessionId").getValue();
        Mono<LottoListResponse> lottoList = webClient.get()
                .uri("/lotto/list/" + page)
                .cookie("sessionId", sessionId)
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(clientResponse -> {
                    System.out.println(clientResponse.statusCode());
                    if (clientResponse.statusCode() == HttpStatus.OK) {
                        return clientResponse.bodyToMono(LottoListResponse.class);
                    }
                    return Mono.empty();
                })
                .doOnNext(lottoListResponse -> System.out.println(lottoListResponse));

        Map<String, Object> model = request.attributes();
        model.put("lottoList", lottoList);
        return ServerResponse.ok().render("users/lottoList", model);

    }

    private void validate(LottoBuyRequest lottoBuyRequest) {
        System.out.println(lottoBuyRequest);
        Errors errors = new BeanPropertyBindingResult(lottoBuyRequest, "lottoBuyRequest");
        lottoBuyRequestValidator.validate(lottoBuyRequest, errors);
        if (errors.hasErrors()) {
            throw new IllegalArgumentException(errors.toString());
        }
    }
}
