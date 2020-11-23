package co.com.jsierra.webfluxrabbitmq;


import co.com.jsierra.webfluxrabbitmq.services.RabbitSendReceive;
import com.rabbitmq.client.Delivery;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.rabbitmq.Sender;

@AllArgsConstructor
@Component
public class Handler {
    private static final Logger LOGGER = LoggerFactory.getLogger(Handler.class);
    Sender sender;
    Flux<Delivery> deliveryFlux;

    @Autowired
    RabbitSendReceive rabbitSendReceive;

    public Mono<ServerResponse> sendMessage(ServerRequest request) {
        String message = request.headers().header("mensaje").get(0);

        Flux<String> queueResponse = rabbitSendReceive.sendMessage(message)
                .filter(x -> x.isAck())
                .flatMap(
                        x -> Mono.just(new String(x.getOutboundMessage().getBody()))
                );

        return ServerResponse.ok()
                .body(queueResponse, String.class);
    }

    public Mono<ServerResponse> enableListener(ServerRequest request) {
        LOGGER.info("Enable Listener...");
        rabbitSendReceive.listenerRead();
        return ServerResponse.ok()
                .body(Mono.just("Listener Enabled"), String.class);
    }

    public Mono<ServerResponse> readFirtsMessage(ServerRequest request) {
        return ServerResponse.ok()
                .body(rabbitSendReceive.readFirts(), String.class);
    }


    public Mono<ServerResponse> purgeQueue(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .body(rabbitSendReceive.purgeQueue(), Void.class);

    }
}

