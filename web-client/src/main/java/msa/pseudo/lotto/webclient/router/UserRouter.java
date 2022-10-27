package msa.pseudo.lotto.webclient.router;

import lombok.RequiredArgsConstructor;
import msa.pseudo.lotto.webclient.filter.FilterFactory;
import msa.pseudo.lotto.webclient.handler.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@RequiredArgsConstructor
public class UserRouter {

    private final FilterFactory filterFactory;

    // Thymeleaf
    @Bean
    public RouterFunction<ServerResponse> usersRoute(UserHandler userHandler) {
        return RouterFunctions.nest(
                RequestPredicates.path("/users"),
                RouterFunctions.route()
                        .GET("/login", userHandler::loginForm)
                        .GET("/logout", userHandler::logout)
                        .GET("/join", userHandler::joinForm)
                        .build()
        );
    }

    // REST
    @Bean
    public RouterFunction<ServerResponse> usersRestRoute(UserHandler userHandler) {
        return RouterFunctions.nest(
                RequestPredicates.path("/users"),
                RouterFunctions.route()
                        .POST("/login", userHandler::login)
                        .POST("/join", userHandler::join)
                        .build()
        );
    }
}
