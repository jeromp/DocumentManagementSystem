package com.github.jeromp.documentmanagementsystem.business.port;

import org.springframework.core.io.Resource;

import java.io.InputStream;

public interface DocumentFilePersistencePort {
    void create(InputStream file, String fileName);

    Resource readAsResource(String fileName);

    InputStream readAsInputStream(String fileName);

    String readMimeType(String fileName);
}
