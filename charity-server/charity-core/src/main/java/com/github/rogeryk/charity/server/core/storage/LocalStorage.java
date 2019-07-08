package com.github.rogeryk.charity.server.core.storage;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class LocalStorage implements Storage {

    private Path rootPath;

    private String address;


    public LocalStorage(String storagePath, String address) {
        this.address = address;
        rootPath = Paths.get(storagePath);
        if (!Files.isDirectory(rootPath)) {
            try {
                Files.createDirectories(rootPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String store(InputStream inputStream, String contentType, String keyName) {

        try {
            Files.copy(inputStream, rootPath.resolve(keyName), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return address+keyName;
    }
}
