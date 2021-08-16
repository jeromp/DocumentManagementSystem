package com.github.jeromp.DocumentManagementSystem.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class DocumentStorageService implements StorageService {
    private final Path rootLocation;

    private StorageProperties properties;

    @Autowired
    public DocumentStorageService(StorageProperties properties){
        this.properties = properties;
        this.rootLocation = Paths.get(this.properties.getRootPath());
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(this.rootLocation);
        }
        catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }

    @Override
    public Path load(String fileName) {
        return this.rootLocation.resolve(fileName);
    }

    @Override
    public void store(MultipartFile file, String fileName) {
        try {
            if(file.isEmpty()){
                throw new StorageException("");
            }
            Path destination = this.rootLocation.resolve(
                    Paths.get(fileName))
                    .normalize().toAbsolutePath();
            if(!destination.getParent().equals(this.rootLocation.toAbsolutePath())) {
                throw new StorageException("Cannot store file outside current directory");
            }
            if(Files.exists(load(fileName))){
                throw new StorageException("File exists.");
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destination,
                        StandardCopyOption.REPLACE_EXISTING);
            }
        }
        catch(IOException e) {
            throw new StorageException("Failed to store file.", e);
        }
    }
}
