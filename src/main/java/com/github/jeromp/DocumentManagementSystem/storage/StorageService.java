package com.github.jeromp.DocumentManagementSystem.storage;

import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Path;

public interface StorageService {

    void init();

    void store(MultipartFile file, String fileName);

    Path load(String fileName);

}