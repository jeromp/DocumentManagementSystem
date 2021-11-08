package com.github.jeromp.documentmanagementsystem.business.adapter;

import com.github.jeromp.documentmanagementsystem.business.port.DocumentFilePersistencePort;
import com.github.jeromp.documentmanagementsystem.persistence.storage.DocumentStorageService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.InputStream;

public class DocumentFilePersistenceAdapter implements DocumentFilePersistencePort {
    @Autowired
    private DocumentStorageService documentStorageService;

    @Override
    public void create(InputStream file, String fileName) {
        this.documentStorageService.create(file, fileName);
    }

    @Override
    public void read(String fileName) {

    }

    @Override
    public void delete(String fileName) {

    }
}
