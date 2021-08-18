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

    private static final StorageProperties properties = new StorageProperties();
    private static DocumentStorageService service = new DocumentStorageService(properties);

    private static final String FILE_NAME = "test.txt";
    private static final MockMultipartFile TEST_FILE = new MockMultipartFile("test", "otherFileName.txt", MediaType.TEXT_PLAIN_VALUE,
                "Hello, World".getBytes());

    @Test
    @DisplayName("load non existent file")
    void loadNonExistent(){
        assertFalse(Files.exists(service.read("non-existent.txt")));
    }

    @Test
    @DisplayName("save and load a file")
    void saveAndLoad(){
        service.create(TEST_FILE, FILE_NAME);
        assertTrue(Files.exists(service.read(FILE_NAME)));
    }

    @Test
    @DisplayName("override an existing file")
    void overrideFile(){
        service.create(TEST_FILE, FILE_NAME);
        assertThrows(StorageException.class, () -> service.create(new MockMultipartFile("test", "otherFileName.txt", MediaType.TEXT_PLAIN_VALUE,
                "Hello, other World".getBytes()), FILE_NAME));
    }

    @Test
    @DisplayName("save an empty file")
    void saveEmptyFile(){
        assertThrows(StorageException.class, () -> service.create(new MockMultipartFile("test", "", null ,
                (byte[]) null), FILE_NAME));
    }

    @Test
    @DisplayName("save file with directory")
    void saveFileAndDirectory(){
        assertThrows(StorageException.class, () -> service.create(TEST_FILE, "testDirectory/" + FILE_NAME));
    }

    @Test
    @DisplayName("delete existing file")
    void deleteExistingFile(){
        service.create(TEST_FILE, FILE_NAME);
        assertTrue(Files.exists(service.read(FILE_NAME)));
        service.delete(FILE_NAME);
        assertFalse(Files.exists(service.read(FILE_NAME)));
    }

    @AfterEach
    void tearDown(){
        assertDoesNotThrow(() ->service.delete(FILE_NAME));
    }

}
