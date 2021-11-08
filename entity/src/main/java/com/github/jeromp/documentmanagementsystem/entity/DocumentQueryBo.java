package com.github.jeromp.documentmanagementsystem.entity;

import java.time.LocalDateTime;
import java.util.UUID;

public class DocumentQueryBo implements BaseBo {
    private String title;
    private String description;
    private LocalDateTime documentCreatedBefore;
    private LocalDateTime documentCreatedAfter;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDocumentCreatedBefore() {
        return documentCreatedBefore;
    }

    public void setDocumentCreatedBefore(LocalDateTime documentCreatedBefore) {
        this.documentCreatedBefore = documentCreatedBefore;
    }

    public LocalDateTime getDocumentCreatedAfter() {
        return documentCreatedAfter;
    }

    public void setDocumentCreatedAfter(LocalDateTime documentCreatedAfter) {
        this.documentCreatedAfter = documentCreatedAfter;
    }
}
