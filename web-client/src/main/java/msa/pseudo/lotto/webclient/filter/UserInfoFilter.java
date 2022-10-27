package msa.pseudo.lotto.webclient.filter;

import lombok.RequiredArgsConstructor;
import msa.pseudo.lotto.webclient.dto.UserInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UserInfoFilter implements WebFilter {

    private final WebClient webClient;
    private static final String SESSION_ID = "sessionId";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        if (!request.getCookies().containsKey(SESSION_ID)) {
            return chain.filter(exchange);
        }

        String sessionId = request.getCookies().getFirst(SESSION_ID).getValue();
        System.out.println(sessionId);
        Mono<UserInfo> userInfo = webClient.get()
                .uri("/users")
                .cookie("sessionId", sessionId)
                .exchangeToMono(clientResponse -> {
                    if (clientResponse.statusCode() == HttpStatus.OK) {
                        return clientResponse.bodyToMono(UserInfo.class);
                    }
                    return Mono.empty();
                });



        return chain.filter(exchange);
    }
}
