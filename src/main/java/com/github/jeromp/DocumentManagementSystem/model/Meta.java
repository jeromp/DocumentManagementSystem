package com.github.jeromp.DocumentManagementSystem.model;

import java.util.UUID;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/*
Model class for Meta data
*/

@Entity
@Table(name="META")
public class Meta implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private UUID id;

    @Column(name="document_id")
    private UUID documentId;

    @Column(name="meta_key")
    private String key;

    @Column(name="meta_value")
    private String value;

    @Column(name="created")
    private LocalDateTime created;

    public Meta() {
        this.created = LocalDateTime.now();
    }

    public Meta(UUID documentId, String key, String value){
        this.documentId = documentId;
        this.key = key;
        this.value = value;
        this.created = LocalDateTime.now();
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey(){
        return this.key;
    }

    public void setValue(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }

    public void setDocumentId(UUID id) {
        this.documentId = id;
    }

    public UUID getDocumentId(){
        return this.documentId;
    }

    public UUID getId() {
        return this.id;
    }
}

