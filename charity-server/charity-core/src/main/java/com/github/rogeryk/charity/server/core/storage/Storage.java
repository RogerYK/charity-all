package com.github.rogeryk.charity.server.core.storage;

import java.io.InputStream;

public interface Storage {

    String store(InputStream inputStream, String contentType, String keyName);
}
