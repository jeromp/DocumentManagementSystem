package com.github.jeromp.DocumentManagementSystem.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class DocumentStorageService implements CrudStorageService {
    private final Path rootLocation;

    private final StorageProperties properties;

    @Autowired
    public DocumentStorageService(StorageProperties properties){
        this.properties = properties;
        this.rootLocation = Paths.get(properties.getRootPath());
    }

    @Override
    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(this.rootLocation);
        }
        catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }

    @Override
    public Path read(String fileName) {
        return this.rootLocation.resolve(fileName);
    }

    @Override
    public void create(MultipartFile file, String fileName) {
        try {
            if(file.isEmpty()){
                throw new StorageException("No file is provided.");
            }
            Path destination = this.getPath(fileName);
            if(!destination.getParent().equals(this.rootLocation.toAbsolutePath())) {
                throw new StorageException("Cannot store file outside current directory");
            }
            if(Files.exists(read(fileName))){
                throw new StorageException("File with same name exists.");
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

    @Override
    public void delete(String fileName){
        try {
            Path file = this.getPath(fileName);
            Files.deleteIfExists(file);

        } catch(IOException e) {
            throw new StorageException("Failed to delete file.", e);
        }
    }

    private Path getPath(String fileName){
        return this.rootLocation.resolve(
                        Paths.get(fileName))
                .normalize().toAbsolutePath();
    }
}
