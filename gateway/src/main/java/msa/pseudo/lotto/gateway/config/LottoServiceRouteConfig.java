package msa.pseudo.lotto.gateway.config;

import lombok.RequiredArgsConstructor;
import msa.pseudo.lotto.gateway.filters.SessionCheckFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class LottoServiceRouteConfig {

    @Bean
    public RouteLocator lottoServiceRoute(RouteLocatorBuilder builder, SessionCheckFilter sessionCheckFilter) {
        return builder.routes()
//                .route(predicateSpec ->
//                        predicateSpec.path("/lottery/**")
//                                .filters(gatewayFilterSpec ->
//                                        gatewayFilterSpec.filter(sessionCheckFilter.apply(new SessionCheckFilter.Config())))
//                                .uri("lb://lotto-service/")
//                )
                .route(predicateSpec ->
                        predicateSpec
                                .path("/lottoRound/**")
                                .uri("lb://lotto-service/")
                )
                .route(predicateSpec ->
                        predicateSpec
                                .path("/lotto/list/**")
                                .filters(gatewayFilterSpec ->
                                        gatewayFilterSpec.filter(sessionCheckFilter.apply(new SessionCheckFilter.Config()))
                                )
                                .uri("lb://lotto-service/")
                )
                .build();
    }
}
