package com.github.jeromp.documentmanagementsystem.dto;

public class MetaDto {
    private String description;
    private String documentCreated;

    public MetaDto(String description, String documentCreated) {
        this.description = description;
        this.documentCreated = documentCreated;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDocumentCreated() {
        return documentCreated;
    }

    public void setDocumentCreated(String documentCreated) {
        this.documentCreated = documentCreated;
    }
}
