package com.github.jeromp.DocumentManagementSystemTest.storage;

import com.github.jeromp.DocumentManagementSystem.storage.DocumentStorageService;
import com.github.jeromp.DocumentManagementSystem.storage.StorageException;
import com.github.jeromp.DocumentManagementSystem.storage.StorageProperties;
import org.junit.jupiter.api.*;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("DocumentStorageServiceTest")
public class DocumentStorageServiceTest {

    private StorageProperties properties = new StorageProperties();
    private DocumentStorageService service;

    private static final String FILE_NAME = "test.txt";

    @BeforeEach
    void init(){
        service = new DocumentStorageService(properties);
        service.init();
    }

    @Test
    @DisplayName("load non existent file")
    void loadNonExistent(){
        assertFalse(Files.exists(service.load("non-existent.txt")));
    }

    @Test
    @DisplayName("save and load a file")
    void saveAndLoad(){
        service.store(new MockMultipartFile("test", "otherFileName.txt", MediaType.TEXT_PLAIN_VALUE,
                "Hello, World".getBytes()), FILE_NAME);
        assertTrue(Files.exists(service.load(FILE_NAME)));
    }

    @Test
    @DisplayName("override an existing file")
    void overrideFile(){
        service.store(new MockMultipartFile("test", "otherFileName.txt", MediaType.TEXT_PLAIN_VALUE,
                "Hello, World".getBytes()), FILE_NAME);
        assertThrows(StorageException.class, () -> service.store(new MockMultipartFile("test", "otherFileName.txt", MediaType.TEXT_PLAIN_VALUE,
                "Hello, other World".getBytes()), FILE_NAME));
    }

    @Test
    @DisplayName("save file with directory")
    void saveFileAndDirectory(){
        assertThrows(StorageException.class, () -> service.store(new MockMultipartFile("test", "otherFileName.txt", MediaType.TEXT_PLAIN_VALUE,
                "Hello, World".getBytes()), "testDirectory/" + FILE_NAME));
    }

    @AfterEach
    void tearDown(){
        try {
            service.delete(FILE_NAME);
        } catch(StorageException e) {

        }
    }

}
