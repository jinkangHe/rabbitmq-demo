package com.rabbitmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@ComponentScan(excludeFilters = {@ComponentScan.Filter(type = FilterType.REGEX,pattern = "com.rabbitmq.mail.*")})
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class,args);
    }
}
