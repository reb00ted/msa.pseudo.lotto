package msa.pseudo.lotto.gateway.handler;

import lombok.RequiredArgsConstructor;
import msa.pseudo.lotto.gateway.dto.ApiResult;
import msa.pseudo.lotto.gateway.dto.CurrentPoint;
import msa.pseudo.lotto.gateway.dto.UserInfo;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.lang.reflect.ParameterizedType;
import java.time.Duration;

@Component
@RequiredArgsConstructor
public class GatewayHandler {

    private final ReactiveStringRedisTemplate redisTemplate;

    private final WebClient.Builder webClientBuilder;

    public Mono<ServerResponse> getUserInfo(ServerRequest request) {
        if (!request.cookies().containsKey("sessionId")) {
            return ServerResponse.status(HttpStatus.NOT_FOUND).build();
        }

        String sessionId = request.cookies().getFirst("sessionId").getValue();
        return Mono.just(sessionId)
                .flatMap(sessionId_ -> redisTemplate.opsForValue().getAndExpire(sessionId_, Duration.ofMinutes(30L)))
                .defaultIfEmpty("")
                .flatMap(userId -> {
                    if (userId.equals("")) {
                        return ServerResponse.status(HttpStatus.NOT_FOUND).build();
                    }
                    return webClientBuilder.build().get()
                            .uri("http://point-service/point/" + userId)
                            .exchangeToMono(clientResponse -> {
                                if (clientResponse.statusCode() == HttpStatus.OK) {
                                    return clientResponse.bodyToMono(CurrentPoint.class);
                                }
                                return Mono.just(new ApiResult<>());
                            })
                            .flatMap(result -> {
                                if (result == null) {
                                    return ServerResponse.status(HttpStatus.NOT_FOUND).build();
                                } else {
                                    System.out.println(result);
                                    return ServerResponse.ok().bodyValue(result);
                                }
                            });
                });
    }

    public Mono<ServerResponse> logout(ServerRequest request) {
        if (!request.cookies().containsKey("sessionId")) {
            // 로그아웃인데 굳이 404할 필요가 있을까?
            return ServerResponse.status(HttpStatus.OK).build();
        }

        String sessionId = request.cookies().getFirst("sessionId").getValue();
        return Mono.just(sessionId)
                .flatMap(sessionId_ -> redisTemplate.opsForValue().delete(sessionId_))
                .flatMap(bool -> ServerResponse.ok().build());
    }
}
