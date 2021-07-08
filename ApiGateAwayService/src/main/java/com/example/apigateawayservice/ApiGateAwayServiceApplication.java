package com.example.apigateawayservice;

import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

import javax.servlet.http.HttpServletRequest;
import java.util.logging.Logger;

@EnableZuulProxy
@SpringBootApplication
public class ApiGateAwayServiceApplication {


    public static void main(String[] args) {
        SpringApplication.run(ApiGateAwayServiceApplication.class, args);
    }

}
