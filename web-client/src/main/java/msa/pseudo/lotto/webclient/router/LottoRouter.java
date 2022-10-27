package msa.pseudo.lotto.webclient.router;

import msa.pseudo.lotto.webclient.filter.FilterFactory;
import msa.pseudo.lotto.webclient.handler.LottoHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class LottoRouter {

    // Thymeleaf
    @Bean
    public RouterFunction<ServerResponse> lottoRoute(LottoHandler lottoHandler, FilterFactory filterFactory) {
        return RouterFunctions.nest(
                RequestPredicates.path("/lotto"),
                RouterFunctions.route()
                        .GET("/buy", lottoHandler::buyForm).filter(filterFactory.userInfoFilter())
                        .GET("/round/{round}", lottoHandler::roundForm).filter(filterFactory.userInfoFilter())
//                        .GET("/lottoRound/{round}", lottoHandler::lottoRoundForm).filter(filterFactory.userInfoFilter())
                        .GET("/list/{page}", lottoHandler::lottoList).filter(filterFactory.userInfoFilter())
                        .build()
        );
    }

    // REST
    @Bean
    public RouterFunction<ServerResponse> lottoRestRoute(LottoHandler lottoHandler, FilterFactory filterFactory) {
        return RouterFunctions.route()
                .POST("/lotto", lottoHandler::buy)
                .build();
    }
}
