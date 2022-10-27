package msa.pseudo.lotto.webclient.filter;

import lombok.RequiredArgsConstructor;
import msa.pseudo.lotto.webclient.dto.UserInfo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.HandlerFilterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;


@Component
@RequiredArgsConstructor
public class FilterFactory {

    private final WebClient webClient;
    private static final String SESSION_ID = "sessionId";

    public HandlerFilterFunction<ServerResponse, ServerResponse> userInfoFilter() {
        return (request, next) -> {
            if (!request.cookies().containsKey(SESSION_ID)) {
                return next.handle(request);
            }

            String sessionId = request.cookies().getFirst(SESSION_ID).getValue();
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
            request.attributes().put("user", userInfo);
            return next.handle(request);
        };
    }

}
