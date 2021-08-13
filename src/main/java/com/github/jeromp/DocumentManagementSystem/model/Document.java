package com.github.jeromp.DocumentManagementSystem.model;

import java.util.UUID;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/*
Model class for Documents
*/

@Entity
@Table(name="DOCUMENT")
public class Document extends BaseEntity implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private UUID id;

    @Column(name="title")
    private String title;

    @Column(name="path")
    private String path;

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return this.id;
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

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getChanged() {
        return changed;
    }

    public void setChanged(LocalDateTime changed) {
        this.changed = changed;
    }

    @Override
    public String toString(){
        return "ID: " + this.id + " Title: " + this.title;
    }
}

