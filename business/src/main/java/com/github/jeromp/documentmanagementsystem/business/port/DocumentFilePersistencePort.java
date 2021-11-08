package com.github.jeromp.documentmanagementsystem.business.port;

import java.io.InputStream;

public interface DocumentFilePersistencePort {
    void create(InputStream file, String fileName);

    void read(String fileName);

    void delete(String fileName);
}
