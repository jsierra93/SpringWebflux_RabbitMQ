package co.com.jsierra.jmsrabbitmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.core.JmsTemplate;

@SpringBootApplication
public class JmsrabbitmqApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(JmsrabbitmqApplication.class, args);
		JmsTemplate jmsTemplate = context.getBean(JmsTemplate.class);
	}
	}

