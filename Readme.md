# RabbitMq proxy

Pet project translating rest calls to RabbitMQ, using [Spring Cloud Gateway](https://spring.io/projects/spring-cloud-gateway).

# Running project

1. Start RabbitMq server with docker-compose:
```bash
docker-compose up
```
RabbitMq server has predefined single exchange `notifications` with type topic. Exchange is binded with queue `notification.client`, matching all routing key (`*`).

2. Run `Main` method
3. Execute http request [publish-notification.http](./http/publish-notification.http). Service will publish message to exchange specified in application properties 