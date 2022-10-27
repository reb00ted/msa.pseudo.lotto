package msa.pseudo.lotto.webclient.router;

import lombok.RequiredArgsConstructor;
import msa.pseudo.lotto.webclient.filter.FilterFactory;
import msa.pseudo.lotto.webclient.handler.HomeHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@RequiredArgsConstructor
public class HomeRouter {

    private final FilterFactory filterFactory;

    @Bean
    public RouterFunction<ServerResponse> homeRoute(HomeHandler homeHandler) {
        return RouterFunctions.route()
                .GET("/", homeHandler::home).filter(filterFactory.userInfoFilter())
                .build();
    }
}
