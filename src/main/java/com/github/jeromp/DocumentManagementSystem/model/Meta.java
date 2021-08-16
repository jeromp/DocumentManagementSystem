package com.github.jeromp.DocumentManagementSystem.model;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.*;

/*
Model class for Meta data
*/

@Entity
@Table(name="META")
public class Meta extends BaseEntity {

    @Id
    @Column(name="document_id")
    private UUID id;

    @OneToOne
    @PrimaryKeyJoinColumn(name="document_id", referencedColumnName="id")
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

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.setId(document.getId());
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}

