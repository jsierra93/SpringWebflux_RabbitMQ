package co.com.jsierra.webfluxrabbitmq;


import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Optional;

@AllArgsConstructor
@Component
public class Handler {
    private static final Logger LOGGER = LoggerFactory.getLogger(Handler.class);

   /* @Value("${variables.exchange2}")
    private String exchangeName;

    @Value("${variables.routing2}")
    private String routingKey;

    @Value("${variables.queue2}")
    private String queueName;*/

    @Autowired
    RabbitTemplate rabbitTemplate;

    RabbitMqConfig rabbitMqConfig;

    public Mono<ServerResponse> sendMessage(ServerRequest request) {
        String MESSAGE = request.headers().header("mensaje").get(0);
        LOGGER.info("Enviando el mensaje \"" + MESSAGE + "\"...");
        rabbitTemplate.convertAndSend(rabbitMqConfig.exchangeName, rabbitMqConfig.routingKey, MESSAGE);
        return ServerResponse.ok()
                .body(Mono.just(MESSAGE), String.class);
    }

    public Mono<ServerResponse> readMessage(ServerRequest request) {
        rabbitTemplate.setDefaultReceiveQueue(rabbitMqConfig.queueName);
        String message = "";
        Mono<String> response;
        Optional<Message> canRead = Optional.ofNullable(rabbitTemplate.receive());
        if (canRead.isPresent()) {
            message = canRead.get().toString();
            response = Mono.just((message));
        } else {
            response = Mono.just("No hay mensajes disponibles");
        }
        return ServerResponse.ok()
                .body(response, String.class);
    }

// Metodo para escuchar siempre la cola y procesar los mensajes
    @RabbitListener(queues = "${variables.queue2}" )
    public void readQueueListener(String message) {
        LOGGER.info("(Listener) Message read from myQueue : " + message);
    }

}

