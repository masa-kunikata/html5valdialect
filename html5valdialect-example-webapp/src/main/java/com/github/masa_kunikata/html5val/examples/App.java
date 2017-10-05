package com.github.masa_kunikata.html5val.examples;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource({"classpath:config/app-context.xml"})
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
