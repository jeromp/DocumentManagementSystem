package com.github.jeromp.documentmanagementsystem.service;

import com.github.jeromp.documentmanagementsystem.dto.DocumentDto;
import com.github.jeromp.documentmanagementsystem.dto.MetaDto;
import com.github.jeromp.documentmanagementsystem.dto.mapper.DocumentMapper;
import com.github.jeromp.documentmanagementsystem.dto.mapper.MetaMapper;
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

import javax.print.Doc;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class DocumentService {
    private DocumentRepository documentRepository;
    private DocumentStorageService documentStorageService;
    private DocumentMapper documentMapper;

    @Autowired
    public DocumentService(DocumentRepository documentRepository, DocumentStorageService documentStorageService, DocumentMapper documentMapper) {
        this.documentRepository = documentRepository;
        this.documentStorageService = documentStorageService;
        this.documentMapper = documentMapper;
    }

    public DocumentDto read(String uuidString) {
        var uuid = UUID.fromString(uuidString);
        var document = this.documentRepository.findByUuid(uuid).orElseThrow(() -> new DocumentNotFoundException(HttpStatus.NOT_FOUND, "Document with id: " + uuidString + " not found."));
        return this.documentMapper.documentToDocumentDto(document);
    }

    public DocumentDto create(MultipartFile file, String title, String description, String isoDocumentCreated) {
        var uuid = UUID.randomUUID();
        String fileName = createFileName(file.getOriginalFilename(), title, uuid);
        this.documentStorageService.create(file, fileName);

        var document = this.documentMapper.mapPartsToDocument(title, uuid, fileName, description, isoDocumentCreated);

        document = this.documentRepository.save(document);
        return this.documentMapper.documentToDocumentDto(document);
    }

    private String createFileName(String oldFile, String title, UUID id) {
        return Optional.ofNullable(oldFile)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(oldFile.lastIndexOf(".")))
                .map(ext -> title + "-" + id + ext).orElseThrow(() -> new StorageException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not create file name"));
    }
}
