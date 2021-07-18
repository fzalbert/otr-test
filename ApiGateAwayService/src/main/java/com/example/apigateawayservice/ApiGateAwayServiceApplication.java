package com.example.apigateawayservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class ApiGateAwayServiceApplication {

    @Value("${rout.auth.url}")
    private String authUrl;
    @Value("${rout.client.url}")
    private String clientUrl;
    @Value("${rout.employee.url}")
    private String employeeUrl;
    @Value("${rout.appeal.url}")
    private String appealUrl;


    public static void main(String[] args) {
        SpringApplication.run(ApiGateAwayServiceApplication.class, args);
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("client", r -> r.path("/client/**")
                        .uri(clientUrl))
                .route("auth", r -> r.path("/auth/**")
                        .uri(authUrl))
                .route("employee", r -> r.path("/employee/**")
                        .uri(employeeUrl))
                .route("appeal", r -> r.path("/appeal/**")
                        .uri(appealUrl))
                .build();
    }
}
