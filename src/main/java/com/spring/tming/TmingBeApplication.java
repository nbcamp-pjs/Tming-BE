package com.spring.tming;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TmingBeApplication {

    public static void main(String[] args) {
        SpringApplication.run(TmingBeApplication.class, args);
    }
}
