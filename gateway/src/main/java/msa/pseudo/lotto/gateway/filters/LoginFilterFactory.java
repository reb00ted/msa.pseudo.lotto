package msa.pseudo.lotto.gateway.filters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import msa.pseudo.lotto.gateway.dto.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.UUID;

@Component
public class LoginFilterFactory extends AbstractGatewayFilterFactory<LoginFilterFactory.Config> {

    @Autowired
    private ReactiveStringRedisTemplate redisTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    public LoginFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        // Custom pre filter
        return (exchange, chain) -> {
            // 사전 처리
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();
            String s = request.getPath().toString();
            System.out.println(s);
            System.out.println(request.getMethod());
            System.out.println(request.getURI().toString());
            System.out.println(request.getRemoteAddress().toString());
            System.out.println("로그인 시도");
//            request.getBody().map(dataBuffer -> dataBuffer.toString(StandardCharsets.UTF_8))
//                    .subscribe(str -> System.out.println(str));

            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                // 사후 처리
                ServerHttpResponse res = exchange.getResponse();
//                res.
                System.out.println(response.getStatusCode());
//                if (response.getStatusCode() != HttpStatus.OK) {
//                    return;
//                }

//                exchange.get

                String sessionId = UUID.randomUUID().toString();
                System.out.println(sessionId);
//                DataBufferFactory dataBufferFactory = response.bufferFactory();
//                DefaultDataBufferFactory joinedBuffers = new DefaultDataBufferFactory().join(dataBufferFactory);

//                System.out.println(s1);
                request.getBody().map(dataBuffer -> dataBuffer.toString(StandardCharsets.UTF_8))
                        .next()
                        .doOnNext(data -> System.out.println(data))
                        .map(this::strToLoginRequest)
                        .subscribe(loginRequest -> System.out.println(loginRequest.toString()));
//                        .flatMap(loginRequest -> {
//                            Mono<Boolean> isSuccess = redisTemplate.opsForValue().set(sessionId, loginRequest.getUserId(), Duration.ofMinutes(30L));
//                            return isSuccess;
//                        })
//                        .subscribe(isSuccess -> {
//                            if (isSuccess.booleanValue()) {
//                                response.getHeaders().add("Set-Cookie", String.format("sessionId=%s; HttpOnly", sessionId));
//                            }
//                        });
            }));
        };
    }

    public static class Config {
        // put the configuration properties
    }

    private LoginRequest strToLoginRequest(String str) {
        try {
            return objectMapper.readValue(str, LoginRequest.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
