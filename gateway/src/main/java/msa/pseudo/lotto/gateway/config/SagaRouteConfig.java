package msa.pseudo.lotto.gateway.config;

import lombok.RequiredArgsConstructor;
import msa.pseudo.lotto.gateway.filters.AddTransactionIdFilter;
import msa.pseudo.lotto.gateway.filters.SessionCheckFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;

@Configuration
@RequiredArgsConstructor
public class SagaRouteConfig {

    private final ReactiveStringRedisTemplate redisTemplate;

    @Bean
    public RouteLocator sagaRoute(RouteLocatorBuilder builder, SessionCheckFilter sessionCheckFilter, AddTransactionIdFilter addTransactionIdFilter) {
        return builder.routes()
                .route(predicateSpec ->
                        predicateSpec
                                .path("/lotto").and()
                                .method(HttpMethod.POST)
                                .filters(gatewayFilterSpec ->
                                        gatewayFilterSpec
                                                .filter(sessionCheckFilter.apply(new SessionCheckFilter.Config()))
                                                .filter(((exchange, chain) -> {
                                                    HttpHeaders headers = exchange.getRequest().getHeaders();
                                                    headers.forEach((k, v) -> System.out.printf("%s %s\n", k, v));
                                                    return chain.filter(exchange);
                                                }))
                                                .filter(addTransactionIdFilter.apply(new AddTransactionIdFilter.Config()))
                                )
                                .uri("lb://saga/")
                )
                .build();

    }
}
