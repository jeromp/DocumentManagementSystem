package com.github.jeromp.documentmanagementsystem.persistence.storage;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Path;

public interface CrudStorageService {

    void create(InputStream file, String fileName);

    void delete(String fileName);

    Path read(String fileName);

}