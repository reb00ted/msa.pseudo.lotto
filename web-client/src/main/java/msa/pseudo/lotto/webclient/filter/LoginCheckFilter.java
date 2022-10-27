package msa.pseudo.lotto.webclient.filter;

import lombok.RequiredArgsConstructor;
import org.apache.http.HttpHeaders;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LoginCheckFilter implements WebFilter {

    private final AntPathMatcher antPathMatcher;
    private final List<String> loginNecessaryPatternList = List.of("/lotto", "/lotto/buy",
            "/point/**", "/lotto/list/**");

    private final String SESSION_ID = "sessionId";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        // 로그인이 필요하지 않은 서비스이거나 로그인 상태라면 pass
        if (!isLoginRequired(path) || hasSessionId(request.getCookies())) {
            return chain.filter(exchange);
        }

        // 로그인 화면으로 리다이렉트
        URI uri = request.getURI();
        String redirectURI = String.format("%s://%s:%d/users/login?redirectURL=%s",
                uri.getScheme(), uri.getHost(), uri.getPort(), uri.getPath());
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.TEMPORARY_REDIRECT);
        response.getHeaders().add(HttpHeaders.LOCATION, redirectURI);
        return response.setComplete();
    }

    private boolean isLoginRequired(String path) {
        for (String pattern : loginNecessaryPatternList) {
            if (antPathMatcher.match(pattern, path)) {
                return true;
            }
        }
        return false;
    }

    private boolean hasSessionId(MultiValueMap<String, HttpCookie> cookies) {
        return cookies.containsKey(SESSION_ID);
    }
}
