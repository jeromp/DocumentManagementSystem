package com.github.jeromp.documentmanagementsystem.rest;

import com.github.jeromp.documentmanagementsystem.dto.DocumentDto;
import com.github.jeromp.documentmanagementsystem.service.DocumentService;
import com.github.jeromp.documentmanagementsystem.utils.UuidIsValid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/documents")
@Tag(name = "document", description = "Document Api")
@Validated
public class DocumentResource {

    private DocumentService service;

    @Autowired
    public DocumentResource(DocumentService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public DocumentDto get(@PathVariable(value = "id") @UuidIsValid String id) {
        return service.read(id);
    }

    @Operation(
            summary = "Create document",
            description = "create document with optional metadata"
    )
    @PostMapping(value = "/", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public DocumentDto post(@Parameter(description = "document title") @RequestPart(name = "title") @NotBlank String title,
                            @Parameter(description = "optional description") @RequestPart(name = "description", required = false) String description,
                            @Parameter(description = "creation date of document") @RequestPart(name = "document_created", required = false) String isoDocumentCreated,
                            @Parameter(description = "document file", content = @Content(mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE)) @RequestPart("file") @NotNull MultipartFile file) {
        return service.create(file, title, description, isoDocumentCreated);
    }
}
