package msa.pseudo.lotto.webclient.handler;

import lombok.RequiredArgsConstructor;
import msa.pseudo.lotto.webclient.dto.UserInfo;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class HomeHandler {

    private final WebClient webClient;

    public Mono<ServerResponse> home(ServerRequest request) {
        // request에 있던 Publisher<T>들은 모델에 등록시켜줘야 함!!
        Map<String, Object> attributes = request.attributes();
        return ServerResponse.ok().render("index", request.attributes());
    }
}
