package pl.grandys.gateway;

import org.springframework.web.server.ServerWebExchange;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;
import java.util.function.Function;

class RequestUriMapper implements Function<ServerWebExchange, Optional<URI>> {

    @Override
    public Optional<URI> apply(ServerWebExchange serverWebExchange) {
        try {
            // GatewayFilterSpec.setPath doesn't support default vhost (due to applying url encoding by default)
            return Optional.of(new URI("http://localhost:15672/api/exchanges/%2F/notifications/publish"));
        } catch (URISyntaxException e) {
            return Optional.empty();
        }
    }
}
