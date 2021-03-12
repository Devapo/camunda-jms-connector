package com.dpoint.tutorial;

import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableProcessApplication
@ComponentScan(basePackages = {"com.example.demo"})
public class Main {
    public static void main(String args[]) {
        SpringApplication.run(Main.class);
    }

}
