package com.github.jeromp.documentmanagementsystem.rest;

import com.github.jeromp.documentmanagementsystem.dto.DocumentDto;
import com.github.jeromp.documentmanagementsystem.service.DocumentService;
import com.github.jeromp.documentmanagementsystem.utils.IsUuidValid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/documents")
@Validated
public class DocumentResource {

    private DocumentService service;

    @Autowired
    public DocumentResource(DocumentService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public DocumentDto get(@PathVariable(value = "id") @IsUuidValid String id) {
        return service.read(id);
    }

    @PostMapping(value = "/")
    public DocumentDto post(@RequestPart(name = "title") @NotBlank String title,
                            @RequestPart(name = "description", required = false) String description,
                            @RequestPart(name = "document_created", required = false) String isoDocumentCreated,
                            @RequestPart("file") @NotNull MultipartFile file) {
        return service.create(file, title, description, isoDocumentCreated);
    }
}
