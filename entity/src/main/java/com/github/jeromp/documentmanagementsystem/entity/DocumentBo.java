package com.github.jeromp.documentmanagementsystem.entity;

import java.util.UUID;

public class DocumentBo implements BaseBo {
    private UUID uuid;
    private String title;
    private String path;
    private MetaBo meta;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
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
