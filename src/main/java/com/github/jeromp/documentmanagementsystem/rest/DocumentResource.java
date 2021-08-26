package com.github.jeromp.documentmanagementsystem.rest;

import com.github.jeromp.documentmanagementsystem.dto.DocumentDto;
import com.github.jeromp.documentmanagementsystem.model.Document;
import com.github.jeromp.documentmanagementsystem.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/documents")
public class DocumentResource {

    private DocumentService service;

    @Autowired
    public DocumentResource(DocumentService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public DocumentDto get(@PathVariable(value = "id") String id) {
        return service.read(id);
    }

    @PostMapping(value = "/")
    public DocumentDto post(@RequestPart(name = "title") @NotNull String title,
                            @RequestPart(name = "description", required = false) String description,
                            @RequestPart(name = "document_created", required = false) String isoDocumentCreated,
                            @RequestPart("file") @Valid @NotBlank MultipartFile file) {
        return service.create(file, title, description, isoDocumentCreated);
    }
}
