package nl.quintor.academy.userservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.BodyExtractors.toMono;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@SpringBootApplication
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

    @Autowired
    private UserRepository userRepository;

    @Bean
    RouterFunction<ServerResponse> getUsers() {
        return
                route(GET("/users"),
                        request -> ok().body(
                                userRepository.findAll(), User.class))
                        .and(
                                route(POST("/users"),
                                        request -> request.body(toMono(User.class))
                                                .doOnNext(user -> userRepository.save(user).subscribe())
                                                .then(ok().build())))
                        .and(route(DELETE("/users"), request -> userRepository.deleteAll().then(ok().build())));
    }
}
