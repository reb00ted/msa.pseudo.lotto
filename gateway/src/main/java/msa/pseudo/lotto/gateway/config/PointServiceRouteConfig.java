package msa.pseudo.lotto.gateway.config;

import lombok.RequiredArgsConstructor;
import msa.pseudo.lotto.gateway.filters.SessionCheckFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

@Configuration
@RequiredArgsConstructor
public class PointServiceRouteConfig {

    @Bean(name = "pointServiceRoutes")
    public RouteLocator pointServiceRoute(RouteLocatorBuilder builder, SessionCheckFilter sessionCheckFilter) {
        return builder.routes()
                .route(predicateSpec ->
                        predicateSpec
                                .path("/point/**")
                                .filters(gatewayFilterSpec ->
                                        gatewayFilterSpec
                                                .filter(sessionCheckFilter.apply(new SessionCheckFilter.Config()))
                                                .filter(((exchange, chain) -> {
                                                    HttpHeaders headers = exchange.getRequest().getHeaders();
                                                    headers.forEach((k, v) -> System.out.printf("%s %s\n", k, v));
                                                    return chain.filter(exchange);
                                                }))
                                )
                                .uri("lb://point-service/")
                )
                .build();



    }
}
