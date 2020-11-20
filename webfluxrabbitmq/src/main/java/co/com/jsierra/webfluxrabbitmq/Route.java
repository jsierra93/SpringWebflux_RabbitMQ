package co.com.jsierra.webfluxrabbitmq;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import javax.print.attribute.standard.Media;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class Route {

    private static final String GET_MESSAGE = "/sendmessage";
    private static final String READ_FIRTS_MESSAGE = "/readfirtsmessage";

    @Bean
    RouterFunction<ServerResponse> routes(Handler handler) {
        return route(POST(GET_MESSAGE).and(accept(MediaType.APPLICATION_JSON)), handler::sendMessage)
                .andRoute(GET(READ_FIRTS_MESSAGE).and(accept(MediaType.APPLICATION_JSON)), handler::readMessage);
    }
}
