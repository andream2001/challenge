package com.integris.test.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
@ComponentScan("com.integris.test")
@SpringBootApplication
public class SpringBootBatchProcessingApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootBatchProcessingApplication.class, args);
    }

}
