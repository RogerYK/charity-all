package com.github.rogeryk.charity.server;


import com.github.rogeryk.charity.server.core.search.index.ProjectDocument;
import com.github.rogeryk.charity.server.core.search.repository.ProjectDocumentRepository;
import com.github.rogeryk.charity.server.db.domain.Project;
import com.github.rogeryk.charity.server.db.repository.ProjectRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.annotation.PostConstruct;

import io.lettuce.core.dynamic.annotation.Command;

@SpringBootApplication
@EnableJpaAuditing
//@EnableCaching
@EnableAspectJAutoProxy
public class App {


    public static void main(String[] args) {
        //spring data elasticsearch 和 spring data redis 冲突
        System.setProperty("es.set.netty.runtime.available.processors", "false");
        SpringApplication.run(App.class);
    }

}
