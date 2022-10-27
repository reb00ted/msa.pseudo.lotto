package msa.pseudo.lotto.gateway.filters;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class AddTransactionIdFilter extends AbstractGatewayFilterFactory<AddTransactionIdFilter.Config> {

    private final ReactiveStringRedisTemplate redisTemplate;
    private final String TX_ID = "TX_ID";
    public static class Config {

    }

    public AddTransactionIdFilter(ReactiveStringRedisTemplate redisTemplate) {
        super();
        this.redisTemplate = redisTemplate;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            return redisTemplate.opsForValue().increment(TX_ID)
                    .flatMap(txId -> {
                        ServerHttpRequest request = exchange.getRequest();
                        request = request.mutate().header("txId", String.valueOf(txId)).build();
                        return chain.filter(exchange.mutate().request(request).build());
                    });
        };
    }
}
