package com.github.masa_kunikata.html5val.examples;

import com.github.masa_kunikata.html5val.Html5ValDialect;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    public Html5ValDialect html5ValDialect() {
      return new Html5ValDialect();
    }
}
