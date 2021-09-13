package com.github.jeromp.documentmanagementsystem.business.port;

import com.github.jeromp.documentmanagementsystem.entity.DocumentBo;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface DocumentServicePort {
    DocumentBo read(String uuidString);

    DocumentBo create(InputStream file, String originalFileName, DocumentBo documentBo);
}
