package com.github.jeromp.DocumentManagementSystem.controller;

import com.github.jeromp.DocumentManagementSystem.model.Document;
import com.github.jeromp.DocumentManagementSystem.model.Meta;
import com.github.jeromp.DocumentManagementSystem.repository.DocumentRepository;
import com.github.jeromp.DocumentManagementSystem.repository.MetaRepository;
import com.github.jeromp.DocumentManagementSystem.storage.DocumentStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/documents")
public class DocumentApiRestController {

    private DocumentRepository documentRepository;
    private DocumentStorageService documentStorageService;
    private MetaRepository metaRepository;

    @Autowired
    public DocumentApiRestController(DocumentRepository documentRepository,
                                     DocumentStorageService documentStorageService,
                                     MetaRepository metaRepository){
        this.documentRepository = documentRepository;
        this.documentStorageService = documentStorageService;
        this.metaRepository = metaRepository;
    }

    @GetMapping("/{id}")
    public Document get(@PathVariable(value="id") String id){
        UUID uuid;
        try {
            uuid = UUID.fromString(id);
        } catch (IllegalArgumentException exception) {
            throw new DocumentNotFoundException(HttpStatus.BAD_REQUEST, "Id not valid");
        }
        var document = this.documentRepository.findByUuid(uuid);
        if(document.isEmpty()) {
            throw new DocumentNotFoundException(HttpStatus.NOT_FOUND, "Document with id: " + id + " not found.");
        }
        return document.get();
    }

    @PostMapping(value = "/")
    public Document post(@RequestPart("title") @NotNull String title,
                         @RequestPart(name = "description", required = false) String description,
                         @RequestPart(name = "document_created", required = false) String isoDocumentCreated,
                         @RequestPart("file") @Valid @NotNull @NotBlank MultipartFile file){
        UUID uuid = UUID.randomUUID();
        String fileName = createFileName(file.getOriginalFilename(), title, uuid);
        this.documentStorageService.create(file, fileName);
        Document document = new Document();
        document.setTitle(title);
        document.setUuid(uuid);
        document.setPath(fileName);
        document = this.documentRepository.save(document);
        if(description != null || isoDocumentCreated != null){
            Meta meta = new Meta();
            meta.setDocument(document);
            meta.setDocumentCreated(LocalDateTime.parse(isoDocumentCreated));
            meta.setDescription(description);
            meta = this.metaRepository.save(meta);
            document.setMeta(meta);
        }
        return document;
    }

    @ExceptionHandler(DocumentNotFoundException.class)
    public void handleDocumentNotFound(DocumentNotFoundException exception, HttpServletResponse response) throws IOException {
        response.sendError(exception.getErrorCode().value(), exception.getMessage());
    }

    private String createFileName(String oldFile, String title, UUID id){
        var extension = Optional.ofNullable(oldFile)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(oldFile.lastIndexOf(".")));
        return title + "-" + id + extension.get();
    }
}
