package com.github.jeromp.DocumentManagementSystem.model;

import java.util.UUID;
import javax.persistence.*;

/*
Model class for Documents
*/

@Entity
@Table(name="DOCUMENT")
public class Document extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="id")
    private Long id;

    @Column(name="uuid")
    private UUID uuid;

    @Column(name="title")
    private String title;

    @Column(name="path")
    private String path;

    @OneToOne(mappedBy = "document", fetch = FetchType.EAGER)
    private Meta meta;

    public Long getId(){
        return this.id;
    }

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

    public void setMeta(Meta meta){
        this.meta = meta;
    }

    @Override
    public String toString(){
        return "ID: " + this.id + " Title: " + this.title;
    }
}

