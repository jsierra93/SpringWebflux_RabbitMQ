package co.com.jsierra.webfluxrabbitmq.health;

import co.com.jsierra.webfluxrabbitmq.models.HealthCheck;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @Value("${project.version}")
    private String projectVersion;

    @RequestMapping(value = "/healthcheck", method = RequestMethod.GET)
    public HealthCheck getHealth() {
        HealthCheck heathCheck = new HealthCheck();
        heathCheck.setVersion(projectVersion);
        return heathCheck;
    }

}