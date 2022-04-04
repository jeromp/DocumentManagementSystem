package com.github.jeromp.documentmanagementsystem.rest.dto;

import com.github.jeromp.documentmanagementsystem.entity.DocumentBo;

import java.util.UUID;

public class DocumentDto {
    private UUID uuid;
    private DocumentDto parent;
    private String type;
    private String title;
    private String path;
    private MetaDto meta;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public DocumentDto getParent() {
        return parent;
    }

    public void setParent(DocumentDto parent) {
        this.parent = parent;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public MetaDto getMeta() {
        return meta;
    }

    public void setMeta(MetaDto meta) {
        this.meta = meta;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
