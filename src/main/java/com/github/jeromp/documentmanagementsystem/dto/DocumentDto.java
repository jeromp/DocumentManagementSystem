package com.github.jeromp.documentmanagementsystem.dto;

import java.util.UUID;

public class DocumentDto {
    private UUID uuid;
    private String title;
    private MetaDto meta;

    public DocumentDto(String title, UUID uuid, MetaDto meta) {
        this.uuid = uuid;
        this.title = title;
        this.meta = meta;
    }

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

    public MetaDto getMeta() {
        return meta;
    }

    public void setMeta(MetaDto meta) {
        this.meta = meta;
    }
}
