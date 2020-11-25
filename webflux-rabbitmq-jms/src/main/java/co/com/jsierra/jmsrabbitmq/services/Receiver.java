package co.com.jsierra.jmsrabbitmq.services;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Receiver {
    @JmsListener(destination = "${variables.queue-name}", containerFactory = "myFactory")
    public void receiveMessage(String messageQueue) {
        System.out.println("Received <" + messageQueue + ">");
    }
}