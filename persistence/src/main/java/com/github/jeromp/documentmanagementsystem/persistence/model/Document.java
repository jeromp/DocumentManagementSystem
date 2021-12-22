package com.github.jeromp.documentmanagementsystem.persistence.model;

import java.util.UUID;
import javax.persistence.*;

/*
Model class for Documents
*/

@Entity
@Table(name = "DOCUMENT")
public class Document extends BaseEntity {

    @Column(name = "uuid")
    private UUID uuid;

    @Column(name = "type")
    private DocumentType type;

    @Column(name = "parent")
    private Document parent;

    @Column(name = "title")
    private String title;

    @Column(name = "path")
    private String path;

    @OneToOne(mappedBy = "document", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Meta meta;

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle(){
        return this.title;
    }

    public void setPath(String path){
        this.path = path;
    }

    public String getPath(){
        return this.path;
    }

    public Meta getMeta() {
        return this.meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public DocumentType getType() {
        return this.type;
    }

    public void setType(DocumentType type) {
        this.type = type;
    }

    public Document getParent() {
        return this.parent;
    }

    public void setParent(Document parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return "ID: " + this.id + " Title: " + this.title;
    }
}

