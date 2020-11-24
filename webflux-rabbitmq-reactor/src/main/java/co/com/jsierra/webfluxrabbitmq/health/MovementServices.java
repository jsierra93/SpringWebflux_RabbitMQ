package co.com.jsierra.webfluxrabbitmq.health;

import co.com.jsierra.webfluxrabbitmq.models.Status;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class MovementServices implements HealthIndicator {

    @Value("${movement.url}")
    private String urlMovement;

    @Override
    public Health health() {
        if (!isRunningMovement()) {
            return Health.down().build();
        }
        return Health.up().build();
    }

    private Boolean isRunningMovement() {
        Status healthResponse = WebClient.create()
                .method(HttpMethod.GET)
                .uri(urlMovement)
                .retrieve()
                .bodyToMono(Status.class)
                .block();
        return healthResponse.getStatus().equals("UP");
    }
}

