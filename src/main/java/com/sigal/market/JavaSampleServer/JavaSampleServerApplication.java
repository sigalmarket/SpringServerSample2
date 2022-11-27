package com.sigal.market.JavaSampleServer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.net.URI;

@SpringBootApplication
public class JavaSampleServerApplication {

    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder.build();
    }

    @Bean
    RouterFunction<ServerResponse> route(WebClient client, @Value("${api.ip}") String apiIp) {
        return RouterFunctions.route()
                .GET("/sayhello", request ->
                        client.get().uri(URI.create("http://" + apiIp + ":8080/hello")).retrieve().bodyToMono(String.class)
                                .flatMap(res -> ServerResponse.ok().bodyValue(res)))
                .GET("/hello", request -> ServerResponse.ok().bodyValue("Hello World!"))
                .build();
    }

    public static void main(String[] args) {
        SpringApplication.run(JavaSampleServerApplication.class, args);
    }

}
