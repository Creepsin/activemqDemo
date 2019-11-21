package com.zh.activemqdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@EnableJms
@SpringBootApplication
public class ActivemqdemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActivemqdemoApplication.class, args);
    }

}
