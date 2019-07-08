package com.github.rogeryk.charity.server.core.storage.config;

import com.github.rogeryk.charity.server.core.storage.LocalStorage;
import com.github.rogeryk.charity.server.core.storage.Storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableConfigurationProperties(StorageProperties.class)
public class StorageConfig {

    @Autowired
    private StorageProperties properties;

    @Bean
    public Storage getStorage() {

        if ("local".equals(properties.getActive())) {
            return createLocalStorage();
        } else {
            throw new RuntimeException("当前储存模式不支持");
        }
    }

    private LocalStorage createLocalStorage() {
        StorageProperties.Local local = properties.getLocal();
        return new LocalStorage(local.getStoragePath(), local.getAddress());
    }
}
