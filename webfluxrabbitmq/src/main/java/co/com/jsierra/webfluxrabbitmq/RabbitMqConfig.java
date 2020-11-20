package co.com.jsierra.webfluxrabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMqConfig {

    @Value("${variables.exchange}")
    public String exchangeName;

    @Value("${variables.routing}")
    public String routingKey;

    @Value("${variables.queue}")
    public String queueName;

    @Value("${variables.durable}")
    private boolean isDurableQueue;

    @Bean
    Queue queue() {
        return new Queue(queueName, isDurableQueue);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(exchangeName);
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
    }
}
