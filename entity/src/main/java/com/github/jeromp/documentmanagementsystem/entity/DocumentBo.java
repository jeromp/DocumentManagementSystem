package com.github.jeromp.documentmanagementsystem.entity;

import java.util.UUID;

public class DocumentBo implements BaseBo {
    private UUID uuid;
    private DocumentBo parent;
    private String type;
    private String title;
    private String path;
    private MetaBo meta;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public DocumentBo getParent() {
        return parent;
    }

    public void setParent(DocumentBo parent) {
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

    public MetaBo getMeta() {
        return meta;
    }

    public void setMeta(MetaBo meta) {
        this.meta = meta;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
