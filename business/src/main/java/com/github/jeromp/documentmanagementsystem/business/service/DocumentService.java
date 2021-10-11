package com.github.jeromp.documentmanagementsystem.business.service;

import com.github.jeromp.documentmanagementsystem.business.port.DocumentPersistencePort;
import com.github.jeromp.documentmanagementsystem.business.port.DocumentServicePort;

import com.github.jeromp.documentmanagementsystem.entity.DocumentBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Optional;
import java.util.UUID;

@Service
public class DocumentService implements DocumentServicePort {

    @Autowired
    private DocumentPersistencePort documentPersistencePort;

    @Autowired
    public DocumentService(DocumentPersistencePort documentPersistencePort) {
        this.documentPersistencePort = documentPersistencePort;
    }

    @Override
    public DocumentBo read(String uuidString) {
        var uuid = UUID.fromString(uuidString);
        return this.documentPersistencePort.findByUuid(uuid);
    }

    @Override
    public DocumentBo create(InputStream file, String originalFileName, DocumentBo documentBo) {
        var uuid = UUID.randomUUID();
        String fileName = createFileName(originalFileName, documentBo.getTitle(), uuid);
        this.documentPersistencePort.create(file, fileName);
        documentBo.setUuid(uuid);
        documentBo.setPath(fileName);
        return this.documentPersistencePort.save(documentBo);
    }

    private String createFileName(String oldFile, String title, UUID id) {
        return Optional.ofNullable(oldFile)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(oldFile.lastIndexOf(".")))
                .map(ext -> title + "-" + id + ext).orElseThrow(() -> new DocumentServiceException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not create file name"));
    }
}
