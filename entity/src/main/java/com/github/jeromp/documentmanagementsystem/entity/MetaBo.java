package com.github.jeromp.documentmanagementsystem.entity;

public class MetaBo implements BaseBo {
    private String description;
    private String documentCreated;

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

