package com.github.jeromp.documentmanagementsystem.model;

import java.time.LocalDateTime;
import javax.persistence.*;

/*
Model class for Meta data
*/

@Entity
@Table(name="META")
public class Meta extends BaseEntity {

    @OneToOne(optional = false, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "document_id", referencedColumnName = "id")
    private Document document;

    @Column(name="description")
    private String description;

    @Column(name="document_created")
    private LocalDateTime documentCreated;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDocumentCreated() {
        return documentCreated;
    }

    public void setDocumentCreated(LocalDateTime documentCreated) {
        this.documentCreated = documentCreated;
    }

    public void setDocument(Document document) {
        this.document = document;
    }
}

