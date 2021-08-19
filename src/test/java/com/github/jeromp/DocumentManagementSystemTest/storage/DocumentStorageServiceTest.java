package com.github.jeromp.DocumentManagementSystemTest.storage;

import com.github.jeromp.DocumentManagementSystem.storage.DocumentStorageService;
import com.github.jeromp.DocumentManagementSystem.storage.DuplicateFileException;
import com.github.jeromp.DocumentManagementSystem.storage.StorageException;
import com.github.jeromp.DocumentManagementSystem.storage.StorageProperties;
import org.junit.jupiter.api.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Document Storage Service unit test")
public class DocumentStorageServiceTest {

    private static final StorageProperties STORAGE_PROPERTIES = new StorageProperties();
    private static DocumentStorageService SERVICE = new DocumentStorageService(STORAGE_PROPERTIES);

    private static final String FILE_NAME = "test.txt";
    private static final MockMultipartFile TEST_FILE = new MockMultipartFile("test", "otherFileName.txt", MediaType.TEXT_PLAIN_VALUE,
                "Hello, World".getBytes());

    @Test
    @DisplayName("load non existent file")
    void loadNonExistent(){
        assertFalse(Files.exists(SERVICE.read("non-existent.txt")));
    }

    @Test
    @DisplayName("save and load a file")
    void saveAndLoad(){
        SERVICE.create(TEST_FILE, FILE_NAME);
        assertTrue(Files.exists(SERVICE.read(FILE_NAME)));
    }

    @Test
    @DisplayName("override an existing file")
    void overrideFile(){
        SERVICE.create(TEST_FILE, FILE_NAME);
        var exception = assertThrows(DuplicateFileException.class, () -> SERVICE.create(new MockMultipartFile("test", "otherFileName.txt", MediaType.TEXT_PLAIN_VALUE,
                "Hello, other World".getBytes()), FILE_NAME));
        assertEquals(HttpStatus.FORBIDDEN, exception.getErrorCode());
    }

    @Test
    @DisplayName("save an empty file")
    void saveEmptyFile(){
        var exception = assertThrows(StorageException.class, () -> SERVICE.create(new MockMultipartFile("test", "", null ,
                (byte[]) null), FILE_NAME));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getErrorCode());
    }

    @Test
    @DisplayName("save file with directory")
    void saveFileAndDirectory(){
        var exception = assertThrows(StorageException.class, () -> SERVICE.create(TEST_FILE, "testDirectory/" + FILE_NAME));
        assertEquals(HttpStatus.FORBIDDEN, exception.getErrorCode());
    }

    @Test
    @DisplayName("delete existing file")
    void deleteExistingFile(){
        SERVICE.create(TEST_FILE, FILE_NAME);
        assertTrue(Files.exists(SERVICE.read(FILE_NAME)));
        SERVICE.delete(FILE_NAME);
        assertFalse(Files.exists(SERVICE.read(FILE_NAME)));
    }

    @AfterEach
    void tearDown(){
        assertDoesNotThrow(() ->SERVICE.delete(FILE_NAME));
    }

}
