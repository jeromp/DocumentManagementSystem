package com.github.jeromp.documentmanagementsystem.business.adapter;

import com.github.jeromp.documentmanagementsystem.business.port.DocumentFilePersistencePort;
import com.github.jeromp.documentmanagementsystem.persistence.storage.DocumentStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;

import java.io.InputStream;

public class DocumentFilePersistenceAdapter implements DocumentFilePersistencePort {
    @Autowired
    private DocumentStorageService documentStorageService;

    @Override
    public void create(InputStream file, String fileName) {
        this.documentStorageService.create(file, fileName);
    }

    @Override
    public Resource readAsResource(String fileName) {
        return this.documentStorageService.readAsResource(fileName);
    }

    @Override
    public InputStream readAsInputStream(String fileName) {
        return this.documentStorageService.readAsInputStream(fileName);
    }

    @Override
    public String readMimeType(String fileName) {
        return this.documentStorageService.readMimeType(fileName);
    }

    @Override
    public boolean delete(String fileName) {
        return this.documentStorageService.delete(fileName);
    }
}
