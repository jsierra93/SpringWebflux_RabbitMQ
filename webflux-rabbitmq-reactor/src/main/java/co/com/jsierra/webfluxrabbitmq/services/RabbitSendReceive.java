package co.com.jsierra.webfluxrabbitmq.services;

import com.rabbitmq.client.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.rabbitmq.*;

import java.util.Optional;

@Service
public class RabbitSendReceive {

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitSendReceive.class);

    private final Sender sender;
    private final Receiver receiver;

    @Autowired
    AmqpAdmin amqpAdmin;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${variables.queue}")
    public String queueName;

    @Value("${variables.autodelete}")
    public boolean autodelete;

    RabbitSendReceive(Mono<Connection> connectionMono) {
        this.receiver = RabbitFlux.createReceiver(new ReceiverOptions().connectionMono(connectionMono));
        this.sender = RabbitFlux.createSender(new SenderOptions().connectionMono(connectionMono));
    }

    public Flux<OutboundMessageResult> sendMessage(String message) {
        Flux<OutboundMessageResult> confirmations = sender.sendWithPublishConfirms(
                Flux.just(new OutboundMessage("", queueName, message.getBytes()))
        );

        return sender.declareQueue(QueueSpecification.queue(queueName).autoDelete(autodelete))
                .thenMany(confirmations)
                .doOnError(e -> LOGGER.error("Envio Fallido", e))
                .doOnComplete(() ->
                    LOGGER.info("Mensaje Enviado: \"" + message + "\"")
                );
    }


    //@Bean se activa para que el metodo este siempre escuchando sin necesidad de ser invocado
    public Disposable listenerRead() {
        return receiver.consumeAutoAck(queueName)
                .delaySubscription(sender.declareQueue(QueueSpecification.queue(queueName).autoDelete(autodelete)))
//                .log()
                .subscribe(
                        msg -> LOGGER.info("(Recibido) Mensaje: {}", new String(msg.getBody()))
                );
    }

    /*
        Metodo para leer cola de mensaje uno por uno (Implementa rabbitTemplate)
     */
    public Mono<String> readFirts() {
        String message = "No hay mensajes disponibles";
        Mono<String> response;
        rabbitTemplate.setDefaultReceiveQueue(queueName);
        Optional<Message> canRead = Optional.ofNullable(rabbitTemplate.receive());
        if (canRead.isPresent()) {
            message = new String(canRead.get().getBody());
            response = Mono.just((message));
        } else {
            response = Mono.just(message);
        }

        LOGGER.info("Leyendo mensaje \"" + message + "\"");

        return response;
    }

    // Metodo para escuchar siempre la cola y procesar los mensajes
  /*  @RabbitListener(queues = "${variables.queue}")
    public void readQueueListener(String message) {
        LOGGER.info("(Listener) Leyendo mensaje : " + message);
    }*/

    public Mono<Void> purgeQueue(){
        LOGGER.info("Purgando cola {}", queueName);
        amqpAdmin.purgeQueue(queueName);
        return Mono.empty();
    }
}
