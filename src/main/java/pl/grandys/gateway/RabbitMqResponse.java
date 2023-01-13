package pl.grandys.gateway;

import org.springframework.cloud.gateway.filter.factory.rewrite.RewriteFunction;
import reactor.core.publisher.Mono;

public class RabbitMqResponse {
    private boolean routed;

    public boolean isRouted() {
        return routed;
    }

    public void setRouted(boolean routed) {
        this.routed = routed;
    }

    static RewriteFunction<RabbitMqResponse, String> responseMapper() {
        return (serverWebExchange, response) -> {
            if (response.routed) {
                return Mono.just("Message was routed!");
            } else {
                return Mono.just("Message was not routed!");
            }
        };

    }
}
