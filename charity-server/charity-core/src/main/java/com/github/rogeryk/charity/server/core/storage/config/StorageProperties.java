package com.github.rogeryk.charity.server.core.storage.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "storage")
public class StorageProperties {

    private String active;

    private Local local;

    @Data
    public static class Local {
        private String address;
        private String storagePath;
    }
}

