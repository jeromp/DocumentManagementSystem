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
public class Document implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private UUID id;

    @Column(name="title")
    private String title;

    @Column(name="path")
    private String path;

    @Column(name="created")
    private LocalDateTime created;

    public Document(){
        this.created = LocalDateTime.now();
    }

    public Document(String title, String path){
        this.title = title;
        this.path = path;
        this.created = LocalDateTime.now();
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

    public UUID getId() {
        return this.id;
    }

    @Override
    public String toString(){
        return "ID: " + this.id + " Title: " + this.title;
    }
}

