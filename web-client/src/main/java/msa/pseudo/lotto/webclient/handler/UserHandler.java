package msa.pseudo.lotto.webclient.handler;

import lombok.RequiredArgsConstructor;
import msa.pseudo.lotto.webclient.dto.user.JoinRequest;
import msa.pseudo.lotto.webclient.dto.user.JoinResponse;
import msa.pseudo.lotto.webclient.dto.user.LoginRequest;
import msa.pseudo.lotto.webclient.dto.user.LoginResponse;
import msa.pseudo.lotto.webclient.validator.JoinRequestValidator;
import msa.pseudo.lotto.webclient.validator.LoginRequestValidator;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;


@Component
@RequiredArgsConstructor
public class UserHandler {

    private final WebClient webClient;
    private final JoinRequestValidator joinRequestValidator;
    private final LoginRequestValidator loginRequestValidator;


    public Mono<ServerResponse> loginForm(ServerRequest request) {
        return ServerResponse.ok().render("users/loginForm");
    }

    public Mono<ServerResponse> joinForm(ServerRequest request) {
        return ServerResponse.ok().render("users/joinForm");
    }

    public Mono<ServerResponse> join(ServerRequest request) {
        return request.bodyToMono(JoinRequest.class)
                .doOnNext(this::validate)
                .flatMap(joinRequest ->
                        webClient.post()
                                .uri("/users/join")
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(Mono.just(joinRequest), JoinRequest.class)
                                .retrieve()
                                .onStatus(status -> status == HttpStatus.NOT_ACCEPTABLE, clientResponse -> Mono.error(new IllegalStateException("이미 존재하는 아이디입니다.")))
                                .onStatus(HttpStatus::isError, clientResponse -> Mono.error(new Exception()))
                                .bodyToMono(JoinResponse.class)
                                .onErrorResume(IllegalStateException.class, e -> Mono.just(new JoinResponse(e.getMessage())))
                                .onErrorResume(IllegalArgumentException.class, e -> Mono.just(new JoinResponse(e.getMessage())))
                                .onErrorResume(Exception.class, e -> Mono.just(new JoinResponse(e.getMessage())))
                )
                .flatMap(joinResponse -> ServerResponse.ok().body(Mono.just(joinResponse), JoinResponse.class))
                .onErrorResume(IllegalArgumentException.class, e -> ServerResponse.status(HttpStatus.BAD_REQUEST).body(
                        Mono.just(new JoinResponse(e.getMessage())), JoinResponse.class)
                );
    }

    public Mono<ServerResponse> login(ServerRequest request) {
        return request.bodyToMono(LoginRequest.class)
                .doOnNext(this::validate)
                .flatMap(loginRequest ->
                        webClient.post()
                                .uri("/users/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(Mono.just(loginRequest), LoginRequest.class)
                                .exchangeToMono(clientResponse -> {
                                    if (clientResponse.statusCode() == HttpStatus.OK) {
                                        // 로그인 성공 시 쿠키로 sessionId 설정
                                        ResponseCookie sessionIdCookie = clientResponse.cookies().getFirst("sessionId");
                                        return clientResponse.bodyToMono(LoginResponse.class)
                                                .map(response -> {
                                                    response.setCookie(sessionIdCookie);
                                                    return response;
                                                });
                                    }

                                    return Mono.just(new LoginResponse());
                                })
//                                .onErrorResume(IllegalStateException.class, e -> Mono.just(new LoginResponse(e.getMessage())))
                )
                .flatMap(loginResponse -> ServerResponse.ok()
                                    .cookie(loginResponse.getCookie())
                                    .body(Mono.just(loginResponse), LoginResponse.class)
                )
                .onErrorResume(IllegalArgumentException.class, e -> ServerResponse.status(HttpStatus.BAD_REQUEST).body(
                        Mono.just(new LoginResponse(e.getMessage())), LoginResponse.class)
                );
    }

    public Mono<ServerResponse> logout(ServerRequest request) {
        if (!request.cookies().containsKey("sessionId")) {
            return ServerResponse.status(HttpStatus.TEMPORARY_REDIRECT)
                    .header(HttpHeaders.LOCATION, "/")
                    .build();
        }

        String sessionId = request.cookies().getFirst("sessionId").getValue();
        return webClient.get()
                .uri("/users/logout")
                .cookie("sessionId", sessionId)
                .exchangeToMono(res -> Mono.just(""))
                .flatMap(temp -> {
                    ResponseCookie expiredCookie = ResponseCookie.from("sessionId", sessionId).maxAge(0L).path("/").build();
                    return ServerResponse.status(HttpStatus.TEMPORARY_REDIRECT)
                            .header(HttpHeaders.LOCATION, "/")
                            .cookie(expiredCookie)
                            .build();
                });
    }

    private void validate(LoginRequest loginRequest) {
        System.out.println(loginRequest.toString());
        Errors errors = new BeanPropertyBindingResult(loginRequest, "loginRequest");
        loginRequestValidator.validate(loginRequest, errors);

        if (errors.hasErrors()) {
            throw new IllegalArgumentException(errors.toString());
        }
    }


    private void validate(JoinRequest joinRequest) {
        System.out.println(joinRequest.toString());
        Errors errors = new BeanPropertyBindingResult(joinRequest, "joinRequest");
        joinRequestValidator.validate(joinRequest, errors);

        if (errors.hasErrors()) {
            throw new IllegalArgumentException(errors.toString());
        }
    }


}
