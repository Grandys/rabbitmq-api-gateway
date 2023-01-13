package pl.grandys.gateway;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.server.ServerWebExchange;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;
import java.util.function.Function;

@Configuration
class RabbitMqRouteLocator {

    @Bean
    RouteLocator rabbitMqRoutes(
            RouteLocatorBuilder builder,
            Function<ServerWebExchange, Optional<URI>> requestUriMapper,
            RabbitMqConfig config
    ) {
        return builder.routes()
                .route(route ->
                        route.path("/notifications").and().method(HttpMethod.POST)
                                .filters(filter ->
                                        filter.modifyRequestBody(
                                                        ObjectNode.class,
                                                        RabbitMqMessage.class,
                                                        MediaType.APPLICATION_JSON_VALUE,
                                                        RabbitMqMessage.bodyToRabbitMqMapper()
                                                )
                                                .changeRequestUri(requestUriMapper)
                                                .addRequestHeader(
                                                        HttpHeaders.AUTHORIZATION,
                                                        config.getBasicAUthHeaderValue()

                                                ).modifyResponseBody(
                                                        RabbitMqResponse.class,
                                                        String.class,
                                                        RabbitMqResponse.responseMapper()
                                                ).rewriteResponseHeader(
                                                        HttpHeaders.CONTENT_TYPE,
                                                        MediaType.APPLICATION_JSON_VALUE,
                                                        MediaType.TEXT_PLAIN_VALUE
                                                )
                                )
                                // Overriden by uriMapper
                                .uri(config.httpAddress())
                )
                .build();
    }

    @Bean
    Function<ServerWebExchange, Optional<URI>> uriMapper(RabbitMqConfig config) throws URISyntaxException {
        String uriString = String.format("%s/api/exchanges/%s/%s/publish",
                config.httpAddress(),
                config.urlEncodedVhost(),
                config.getExchange()
        );
        URI rabbitMqPublishEndpoint = new URI(uriString);
        return serverWebExchange -> Optional.of(rabbitMqPublishEndpoint);
    }
}
