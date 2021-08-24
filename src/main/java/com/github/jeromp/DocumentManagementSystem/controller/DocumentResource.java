package com.github.jeromp.DocumentManagementSystem.controller;

import com.github.jeromp.DocumentManagementSystem.model.Document;
import com.github.jeromp.DocumentManagementSystem.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.IOException;

@RestController
@RequestMapping("/documents")
public class DocumentResource {

    private DocumentService service;

    @Autowired
    public DocumentResource(DocumentService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public Document get(@PathVariable(value = "id") String id) {
        return service.read(id);
    }

    @PostMapping(value = "/")
    public Document post(@RequestPart(name = "title") @NotNull String title,
                         @RequestPart(name = "description", required = false) String description,
                         @RequestPart(name = "document_created", required = false) String isoDocumentCreated,
                         @RequestPart("file") @Valid @NotBlank MultipartFile file) {
        return service.create(file, title, description, isoDocumentCreated);
    }
}
