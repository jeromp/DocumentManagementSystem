package com.github.jeromp.documentmanagementsystem.business.port;

import com.github.jeromp.documentmanagementsystem.entity.DocumentBo;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface DocumentPersistencePort {
    DocumentBo findByUuid(UUID uuid);

    List<DocumentBo> findAll();

    List<DocumentBo> findByQuery(String title, String description, LocalDateTime documentCreatedAfter, LocalDateTime documentCreatedBefore);

    void create(InputStream file, String fileName);

    DocumentBo save(DocumentBo documentBo);

    void delete(DocumentBo documentBo);
}
