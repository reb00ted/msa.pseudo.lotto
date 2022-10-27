package msa.pseudo.lotto.gateway.filters;

import org.apache.http.HttpHeaders;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.Duration;
import java.util.List;

@Component
public class SessionCheckFilter extends AbstractGatewayFilterFactory<SessionCheckFilter.Config> {

    private final ReactiveStringRedisTemplate redisTemplate;

    public static class Config {

    }

    public SessionCheckFilter(ReactiveStringRedisTemplate redisTemplate) {
        super(Config.class);
        this.redisTemplate = redisTemplate;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            HttpCookie sessionIdCookie = request.getCookies().getFirst("sessionId");
            if (sessionIdCookie == null) {
                System.out.println("sessionId Cookie 없음");
                return unauthorized(exchange.getResponse());
            }

            String sessionId = sessionIdCookie.getValue();
            System.out.println(sessionId);
            return redisTemplate.opsForValue().getAndExpire(sessionId, Duration.ofMinutes(30L))
                    .switchIfEmpty(Mono.just(""))
                    .flatMap(userId -> {
                        System.out.println(userId);
                        if (userId.equals("")) {
                            return unauthorized(exchange.getResponse());
                        } else {
                            ServerHttpRequest mutatedRequest = request.mutate().headers(httpHeaders -> httpHeaders.add("userId", userId)).build();
                            return chain.filter(exchange.mutate().request(mutatedRequest).build());
                        }
                    });
        };
    }

    private Mono<Void> unauthorized(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();
    }
}
