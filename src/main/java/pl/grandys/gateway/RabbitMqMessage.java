package pl.grandys.gateway;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.cloud.gateway.filter.factory.rewrite.RewriteFunction;
import reactor.core.publisher.Mono;

import java.util.Map;

public class RabbitMqMessage {


    private Map<String, String> properties = Map.of();
    private String routing_key;
    private String payload;
    private String payload_encoding = "string";

    public RabbitMqMessage(String routingKey, String payload) {
        this.routing_key = routingKey;
        this.payload = payload;
    }

    public RabbitMqMessage(Map<String, String> properties, String routingKey, String payload, String payload_encoding) {
        this.properties = properties;
        this.routing_key = routingKey;
        this.payload = payload;
        this.payload_encoding = payload_encoding;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    public String getRouting_key() {
        return routing_key;
    }

    public void setRouting_key(String routing_key) {
        this.routing_key = routing_key;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String getPayload_encoding() {
        return payload_encoding;
    }

    public void setPayload_encoding(String payload_encoding) {
        this.payload_encoding = payload_encoding;
    }

    static RewriteFunction<ObjectNode, RabbitMqMessage> bodyToRabbitMqMapper() {
        return (serverWebExchange, body) -> Mono.just(new RabbitMqMessage(
                serverWebExchange.getRequest().getHeaders().getFirst("X-Routing-Key"),
                body.toPrettyString()
        ));
    }
}
