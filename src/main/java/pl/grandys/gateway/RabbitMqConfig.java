package pl.grandys.gateway;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.net.URLEncoder;
import java.util.Base64;

@ConfigurationProperties(prefix = "gateway.rabbitmq")
class RabbitMqConfig {
    private String host;
    private int httpPort;
    private String user;
    private String password;
    private String exchange;
    private String vhost;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getHttpPort() {
        return httpPort;
    }

    public void setHttpPort(int httpPort) {
        this.httpPort = httpPort;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getVhost() {
        return vhost;
    }

    public void setVhost(String vhost) {
        this.vhost = vhost;
    }

    String httpAddress() {
        return String.format("http://%s:%d", host, httpPort);
    }

    String urlEncodedVhost() {
        return URLEncoder.encode(vhost);
    }

    String getBasicAUthHeaderValue() {
        String plainUserPassword = String.format("%s:%s", user, password);
        String base64EncodedCredentials = Base64.getEncoder().encodeToString(plainUserPassword.getBytes());
        return String.format("Basic %s", base64EncodedCredentials);
    }
}
