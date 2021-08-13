package com.github.jeromp.DocumentManagementSystem.model;

import java.util.UUID;
import javax.persistence.*;

/*
Model class for Meta data
*/

@Entity
@Table(name="META")
public class Meta extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private UUID id;

    @ManyToOne
    private Document document;

    @Column(name="meta_key")
    private String key;

    @Column(name="meta_value")
    private String value;

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }
}

