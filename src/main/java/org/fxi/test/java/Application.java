package org.fxi.test.java;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages ="org.fxi.test.java.springboot" )
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}