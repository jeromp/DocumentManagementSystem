package com.github.jeromp.documentmanagementsystem.persistence.model;

import org.hibernate.annotations.Type;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import javax.persistence.*;

/*
Model class for Documents
*/

@Entity
@Table(name = "DOCUMENT")
public class Document extends BaseEntity {

    @Column(columnDefinition = "uuid", name = "uuid")
    //@Type(type = "uuid-char")
    private UUID uuid;

    @Column(name = "type")
    private DocumentType type;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Document parent;

    @OneToMany(mappedBy = "parent", fetch = FetchType.EAGER, targetEntity = Document.class, cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH, CascadeType.REMOVE}, orphanRemoval = true)
    private List<Document> children = new ArrayList<>();

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

    public void setParent(Document parent) throws IllegalRelationshipException {
        if (parent.getType() != DocumentType.DIRECTORY) {
            throw new IllegalRelationshipException(HttpStatus.METHOD_NOT_ALLOWED, "Parent needs to be of type Directory");
        }
        this.parent = parent;
        this.parent.addChildren(this);
    }

    public Collection<Document> getChildren() {
        return children;
    }

    public void setChildren(List<Document> children) {
        this.children = children;
    }

    public void addChildren(Document children) throws IllegalRelationshipException {
        if (this.type != DocumentType.DIRECTORY) {
            throw new IllegalRelationshipException(HttpStatus.METHOD_NOT_ALLOWED, "Parent needs to be of type Directory");
        }
        this.children.add(children);
    }

    @PreRemove
    public void preRemove() {
        if (this.parent != null) {
            this.parent.getChildren().remove(this);
            this.parent = null;
        }
    }

    @Override
    public String toString() {
        return "ID: " + this.id + " Title: " + this.title;
    }


}

