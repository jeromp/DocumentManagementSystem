package com.github.jeromp.documentmanagementsystem.business.service;

import com.github.jeromp.documentmanagementsystem.business.port.DocumentDataPersistencePort;
import com.github.jeromp.documentmanagementsystem.business.port.DocumentFilePersistencePort;
import com.github.jeromp.documentmanagementsystem.business.port.DocumentServicePort;

import com.github.jeromp.documentmanagementsystem.business.service.common.DocumentServiceException;
import com.github.jeromp.documentmanagementsystem.entity.DocumentBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DocumentService implements DocumentServicePort {

    private DocumentDataPersistencePort documentDataPersistencePort;
    private DocumentFilePersistencePort documentFilePersistencePort;

    @Autowired
    public DocumentService(DocumentDataPersistencePort documentDataPersistencePort, DocumentFilePersistencePort documentFilePersistencePort) {
        this.documentDataPersistencePort = documentDataPersistencePort;
        this.documentFilePersistencePort = documentFilePersistencePort;
    }

    @Override
    public Resource readResource(String uuidString) {
        var uuid = UUID.fromString(uuidString);
        var document = this.documentDataPersistencePort.readByUuid(uuid);
        return this.documentFilePersistencePort.readAsResource(document.getPath());
    }

    @Override
    public DocumentBo readBo(String uuidString) {
        var uuid = UUID.fromString(uuidString);
        return this.documentDataPersistencePort.readByUuid(uuid);
    }

    @Override
    public DocumentBo create(InputStream file, String originalFileName, DocumentBo documentBo) {
        var uuid = UUID.randomUUID();
        String fileName = createFileName(originalFileName, documentBo.getTitle(), uuid);
        this.documentFilePersistencePort.create(file, fileName);
        documentBo.setUuid(uuid);
        documentBo.setPath(fileName);
        return this.documentDataPersistencePort.create(documentBo);
    }

    @Override
    public List<DocumentBo> findByQuery(Optional<String> title, Optional<String> description, Optional<LocalDateTime> documentCreatedAfter, Optional<LocalDateTime> documentCreatedBefore) {
        String titleString = title.orElse(null);
        String descriptionString = description.orElse(null);
        LocalDateTime documentCreatedAfterObj = documentCreatedAfter.orElse(null);
        LocalDateTime documentCreatedBeforeObj = documentCreatedBefore.orElse(null);
        return this.documentDataPersistencePort.findByQuery(titleString, descriptionString, documentCreatedAfterObj, documentCreatedBeforeObj);
    }

    private String createFileName(String oldFile, String title, UUID id) {
        return Optional.ofNullable(oldFile)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(oldFile.lastIndexOf(".")))
                .map(ext -> title + "-" + id + ext).orElseThrow(() -> new DocumentServiceException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not create file name"));
    }
}
