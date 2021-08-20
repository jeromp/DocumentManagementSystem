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
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="id")
    private Long id;

    @OneToOne(optional = false, orphanRemoval = true, fetch = FetchType.EAGER)
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

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

