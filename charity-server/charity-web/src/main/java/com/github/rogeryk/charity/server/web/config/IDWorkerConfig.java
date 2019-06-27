package com.github.rogeryk.charity.server.web.config;

import com.github.rogeryk.charity.server.core.util.IDWorker;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.UnknownHostException;

@Configuration
public class IDWorkerConfig {

    @Bean
    public IDWorker idWorker() throws UnknownHostException {
        return new IDWorker();
    }

}
