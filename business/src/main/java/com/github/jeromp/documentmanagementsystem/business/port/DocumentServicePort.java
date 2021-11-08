package com.github.jeromp.documentmanagementsystem.business.port;

import com.github.jeromp.documentmanagementsystem.entity.DocumentBo;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DocumentServicePort {
    Resource readResource(String uuidString);

    DocumentBo readBo(String uuidString);

    DocumentBo create(InputStream file, String originalFileName, DocumentBo documentBo);

    List<DocumentBo> findByQuery(Optional<String> title, Optional<String> description, Optional<LocalDateTime> documentCreatedAfter, Optional<LocalDateTime> documentCreatedBefore);
}
