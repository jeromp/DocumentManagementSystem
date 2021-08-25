package com.github.jeromp.documentmanagementsystem.service;

import com.github.jeromp.documentmanagementsystem.rest.common.DocumentNotFoundException;
import com.github.jeromp.documentmanagementsystem.model.Document;
import com.github.jeromp.documentmanagementsystem.model.Meta;
import com.github.jeromp.documentmanagementsystem.repository.DocumentRepository;
import com.github.jeromp.documentmanagementsystem.storage.DocumentStorageService;
import com.github.jeromp.documentmanagementsystem.storage.StorageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class DocumentService {
    private DocumentRepository documentRepository;
    private DocumentStorageService documentStorageService;

    @Autowired
    public DocumentService(DocumentRepository documentRepository, DocumentStorageService documentStorageService) {
        this.documentRepository = documentRepository;
        this.documentStorageService = documentStorageService;
    }

    public Document read(String uuidString) {
        UUID uuid;
        try {
            uuid = UUID.fromString(uuidString);
            return this.documentRepository.findByUuid(uuid).orElseThrow(() -> new DocumentNotFoundException(HttpStatus.NOT_FOUND, "Document with id: " + uuidString + " not found."));
        } catch (IllegalArgumentException exception) {
            throw new DocumentNotFoundException(HttpStatus.BAD_REQUEST, "Id not valid");
        }
    }

    public Document create(MultipartFile file, String title, String description, String isoDocumentCreated) {
        var uuid = UUID.randomUUID();
        String fileName = createFileName(file.getOriginalFilename(), title, uuid);
        this.documentStorageService.create(file, fileName);

        Document document = new Document();
        document.setTitle(title);
        document.setUuid(uuid);
        document.setPath(fileName);
        if (description != null || isoDocumentCreated != null) {
            Meta meta = new Meta();
            if (isoDocumentCreated != null) {
                meta.setDocumentCreated(LocalDateTime.parse(isoDocumentCreated));
            }
            meta.setDocument(document);
            meta.setDescription(description);
            document.setMeta(meta);
        }
        return this.documentRepository.save(document);
    }

    private String createFileName(String oldFile, String title, UUID id) {
        return Optional.ofNullable(oldFile)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(oldFile.lastIndexOf(".")))
                .map(ext -> title + "-" + id + ext).orElseThrow(() -> new StorageException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not create file name"));
    }
}
