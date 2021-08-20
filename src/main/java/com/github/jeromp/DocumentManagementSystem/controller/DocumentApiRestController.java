package com.github.jeromp.DocumentManagementSystem.controller;

import com.github.jeromp.DocumentManagementSystem.exception.DocumentNotFoundException;
import com.github.jeromp.DocumentManagementSystem.model.Document;
import com.github.jeromp.DocumentManagementSystem.repository.DocumentRepository;
import com.github.jeromp.DocumentManagementSystem.storage.DocumentStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/documents")
public class DocumentApiRestController {

    private DocumentRepository documentRepository;
    private DocumentStorageService documentStorageService;

    @Autowired
    public DocumentApiRestController(DocumentRepository documentRepository, DocumentStorageService documentStorageService){
        this.documentRepository = documentRepository;
        this.documentStorageService = documentStorageService;
    }

    @GetMapping("/{id}")
    public Document get(@PathVariable(value="id") String id){
        UUID uuid;
        try {
            uuid = UUID.fromString(id);
        } catch (IllegalArgumentException exception) {
            throw new DocumentNotFoundException("Id not valid");
        }
        var document = this.documentRepository.findByUuid(uuid);
        if(document.isEmpty()) {
            throw new DocumentNotFoundException("Document with id: " + id + " not found.");
        }
        return document.get();
    }

    @PostMapping("/")
    public Document post(@RequestPart("title") @NotNull String title, @RequestParam("file") @Valid @NotNull @NotBlank MultipartFile file){
        UUID uuid = UUID.randomUUID();
        String fileName = createFileName(file.getOriginalFilename(), title, uuid);
        this.documentStorageService.create(file, fileName);
        Document document = new Document();
        document.setTitle(title);
        document.setUuid(uuid);
        document.setPath(fileName);
        this.documentRepository.save(document);
        return document;
    }

    @ExceptionHandler(DocumentNotFoundException.class)
    public void handleDocumentNotFound(DocumentNotFoundException exception, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value(), exception.getMessage());
    }

    private String createFileName(String oldFile, String title, UUID id){
        var extension = Optional.ofNullable(oldFile)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(oldFile.lastIndexOf(".") + 1));
        return title + "-" + id + "." + extension.get();
    }
}
