package msa.pseudo.lotto.gateway.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import msa.pseudo.lotto.gateway.dto.ApiResult;
import msa.pseudo.lotto.gateway.dto.LoginResponse;
import msa.pseudo.lotto.gateway.filters.LoginFilterFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import javax.ws.rs.HttpMethod;
import java.time.Duration;
import java.util.*;

@Configuration
@RequiredArgsConstructor
public class UserServiceRouteConfig {

    private final ObjectMapper objectMapper;

    private final ReactiveStringRedisTemplate redisTemplate;


    @Bean(name = "userServiceRoutes")
    public RouteLocator userServiceroute(RouteLocatorBuilder builder, LoginFilterFactory loginFilterFactory) {
        return builder.routes()
                .route(predicateSpec -> predicateSpec
                        .path("/users/login").and()
                        .method(HttpMethod.POST)
                        .filters(gatewayFilterSpec ->
                                gatewayFilterSpec.modifyResponseBody(String.class, String.class, (exchange, s) -> {
                                    // 함수 인터페이스 객체로 만들어서 전달해야 할듯
                                    System.out.println(s);
                                    ServerHttpResponse response = exchange.getResponse();
                                    if (response.getStatusCode() != HttpStatus.OK) {
                                        return Mono.just(s);
                                    }

                                    String userId;
                                    try {
                                        LoginResponse result = objectMapper.readValue(s, LoginResponse.class);
                                        System.out.println(result);
                                        userId = result.getUserId();
                                    } catch (JsonProcessingException e) {
                                        throw new RuntimeException(e);
                                    }

                                    String sessionId = UUID.randomUUID().toString();
                                    System.out.println(userId);
                                    System.out.println(sessionId);
                                    // 세션 ID 레디스에 저장 + 응답 쿠키 설정 하나로 묶어야 할 것 같은데...
                                    redisTemplate.opsForValue().set(sessionId, userId, Duration.ofMinutes(30L)).subscribe();

                                    response.addCookie(ResponseCookie.from("sessionId", sessionId)
                                            .path("/")
                                            .httpOnly(true)
                                            .maxAge(Duration.ofMinutes(30L))
                                            .build()
                                    );
                                    System.out.println(s);
                                    return Mono.just(s);
                                })
                        )
                        .uri("lb://user-service"))
                .route(predicateSpec -> predicateSpec
                        .path("/users/**")
//                        .filters(gatewayFilterSpec -> gatewayFilterSpec.filter(loginFilterFactory.apply(new LoginFilterFactory.Config())))
                        .uri("lb://user-service")
                )
                .build();
    }

}
