package com.github.jeromp.documentmanagementsystem.persistence.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
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

    @PostConstruct
    private void init() {
        try {
            Files.createDirectories(this.rootLocation);
        } catch (IOException e) {
            throw new StorageException(HttpStatus.BAD_REQUEST, "Could not initialize storage", e);
        }
    }

    @Override
    public Path read(String fileName) {
        return this.rootLocation.resolve(fileName);
    }

    @Override
    public Resource readAsResource(String fileName) {
        try {
            Path filePath = this.rootLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new StorageException(HttpStatus.NOT_FOUND, "File not found");
            }
        } catch (MalformedURLException e) {
            throw new StorageException(HttpStatus.NOT_FOUND, "File not found");
        }
    }

    @Override
    public void create(InputStream inputStream, String fileName) {
        try (InputStream iS = inputStream) {
            Path destination = this.getPath(fileName);
            if (!destination.getParent().equals(this.rootLocation.toAbsolutePath())) {
                throw new StorageException(HttpStatus.FORBIDDEN, "Cannot store file outside current directory");
            }
            if (Files.exists(read(fileName))) {
                throw new DuplicateFileException(HttpStatus.FORBIDDEN, "File with same name exists.");
            }
            Files.copy(iS, destination, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new StorageException(HttpStatus.BAD_REQUEST, "Failed to store file.", e);
        }
    }

    @Override
    public void delete(String fileName){
        try {
            Path file = this.getPath(fileName);
            Files.deleteIfExists(file);
        } catch(IOException e) {
            throw new StorageException(HttpStatus.BAD_REQUEST, "Failed to delete file.", e);
        }
    }

    private Path getPath(String fileName){
        return this.rootLocation.resolve(Paths.get(fileName)).normalize().toAbsolutePath();
    }
}
