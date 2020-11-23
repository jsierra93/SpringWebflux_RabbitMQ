package co.com.jsierra.webfluxrabbitmq;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class Route {

    private static final String GET_MESSAGE = "/sendmessage";
    private static final String ENABLE_READ_LISTENER = "/enablelistener";
    private static final String READ_FIRTS_MESSAGE = "/readfirts";
    private static final String PURGE_QUEUE = "/purge";


    @Bean
    RouterFunction<ServerResponse> routes(Handler handler) {
        return route(POST(GET_MESSAGE).and(accept(MediaType.APPLICATION_JSON)), handler::sendMessage)
                .andRoute(GET(ENABLE_READ_LISTENER).and(accept(MediaType.APPLICATION_JSON)), handler::enableListener)
                .andRoute(GET(READ_FIRTS_MESSAGE).and(accept(MediaType.APPLICATION_JSON)), handler::readFirtsMessage)
                .andRoute(GET(PURGE_QUEUE).and(accept(MediaType.APPLICATION_JSON)), handler::purgeQueue);
    }
}
