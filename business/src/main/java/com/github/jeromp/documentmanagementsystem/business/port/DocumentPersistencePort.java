package com.github.jeromp.documentmanagementsystem.business.port;

import com.github.jeromp.documentmanagementsystem.entity.DocumentBo;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DocumentPersistencePort {
    DocumentBo findByUuid(UUID uuid);

    List<DocumentBo> findAll();

    void create(InputStream file, String fileName);

    DocumentBo save(DocumentBo documentBo);

    void delete(DocumentBo documentBo);
}
