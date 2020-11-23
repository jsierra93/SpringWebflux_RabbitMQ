package co.com.jsierra.webfluxrabbitmq.health;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class VersionDeploy implements HealthIndicator {

    @Value("${variables.version}")
    private String version;

    @Override
    public Health health() {
       return Health.status(
                version
        ).build();
    }
}

