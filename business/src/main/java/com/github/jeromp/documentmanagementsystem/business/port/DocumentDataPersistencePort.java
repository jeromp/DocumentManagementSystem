package com.github.jeromp.documentmanagementsystem.business.port;

import com.github.jeromp.documentmanagementsystem.entity.DocumentBo;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface DocumentDataPersistencePort {
    DocumentBo readByUuid(UUID uuid);

    List<DocumentBo> readAll();

    List<DocumentBo> findByQuery(String title, String description, LocalDateTime documentCreatedAfter, LocalDateTime documentCreatedBefore);

    DocumentBo create(DocumentBo documentBo);

    void delete(DocumentBo documentBo);
}
