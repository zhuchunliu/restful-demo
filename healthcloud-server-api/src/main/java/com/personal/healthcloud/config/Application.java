package com.personal.healthcloud.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@EnableAutoConfiguration
@Configuration
@ComponentScan(value = "com.personal.healthcloud")
public class Application extends SpringBootServletInitializer {
    public static void main(String[] args){
        SpringApplication.run(Application.class);
    }
}
