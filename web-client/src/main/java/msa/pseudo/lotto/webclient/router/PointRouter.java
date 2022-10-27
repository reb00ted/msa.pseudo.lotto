package msa.pseudo.lotto.webclient.router;

import lombok.RequiredArgsConstructor;
import msa.pseudo.lotto.webclient.filter.FilterFactory;
import msa.pseudo.lotto.webclient.handler.PointHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Component
@RequiredArgsConstructor
public class PointRouter {

    private final FilterFactory filterFactory;

    // Thymeleaf
    @Bean
    public RouterFunction<ServerResponse> pointRoute(PointHandler pointHandler) {
        return RouterFunctions.nest(
                RequestPredicates.path("/point"),
                RouterFunctions.route()
                        .GET("/charge", pointHandler::chargeForm).filter(filterFactory.userInfoFilter())
                        .GET("/withdraw", pointHandler::withdrawForm).filter(filterFactory.userInfoFilter())
                        .GET("/history/{page}", pointHandler::history).filter(filterFactory.userInfoFilter())
                        .build()
        );
    }

    // REST
    @Bean
    public RouterFunction<ServerResponse> pointRestRoute(PointHandler pointHandler) {
        return RouterFunctions.nest(
                RequestPredicates.path("/point"),
                RouterFunctions.route()
                        .POST("/charge", pointHandler::charge).filter(filterFactory.userInfoFilter())
                        .POST("/withdraw", pointHandler::withdraw).filter(filterFactory.userInfoFilter())
                        .build()
        );
    }
}
