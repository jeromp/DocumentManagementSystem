package com.github.jeromp.DocumentManagementSystem.storage;

import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Path;

public interface CrudStorageService {

    void create(MultipartFile file, String fileName);

    void delete(String fileName);

    Path read(String fileName);

}