package msa.pseudo.lotto.gateway.config;

import lombok.RequiredArgsConstructor;
import msa.pseudo.lotto.gateway.handler.GatewayHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Configuration
@RequiredArgsConstructor
public class GatewayRouteConfig {

    private final GatewayHandler gatewayHandler;

    @Bean
    public RouterFunction<ServerResponse> gatewaySelfProcess() {
        return RouterFunctions.route()
                .GET("/users", gatewayHandler::getUserInfo)
                .GET("/users/logout", gatewayHandler::logout)
                .build();
    }
}
