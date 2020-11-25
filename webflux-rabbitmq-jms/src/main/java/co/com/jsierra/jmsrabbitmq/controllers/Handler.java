package co.com.jsierra.jmsrabbitmq.controllers;


import co.com.jsierra.jmsrabbitmq.config.JmsConfig;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Component
public class Handler {
    private static final Logger LOGGER = LoggerFactory.getLogger(Handler.class);

    @Autowired
    JmsTemplate jmsTemplate;

    @Autowired
    JmsConfig jmsConfig;

    public Mono<ServerResponse> sendMessage(ServerRequest request) {
        String message = request.headers().header("message").get(0);
        jmsTemplate.convertAndSend(jmsConfig.queueName, message);
        return ServerResponse.ok()
                .body(Mono.just(message), String.class);
    }

}

