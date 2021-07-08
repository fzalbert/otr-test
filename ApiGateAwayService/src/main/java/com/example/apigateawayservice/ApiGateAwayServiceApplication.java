package com.example.apigateawayservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@SpringBootApplication
public class ApiGateAwayServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGateAwayServiceApplication.class, args);
    }

}
