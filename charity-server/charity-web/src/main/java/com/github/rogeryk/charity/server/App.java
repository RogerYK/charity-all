package com.github.rogeryk.charity.server;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableJpaAuditing
//@EnableCaching
@EnableScheduling
@EnableAspectJAutoProxy
public class App {


    public static void main(String[] args) {
        //spring data elasticsearch 和 spring data redis 冲突
        System.setProperty("es.set.netty.runtime.available.processors", "false");
        SpringApplication.run(App.class);
    }

}
