package com.github.jeromp.documentmanagementsystemtest.storage;

import com.github.jeromp.documentmanagementsystem.storage.DocumentStorageService;
import com.github.jeromp.documentmanagementsystem.storage.DuplicateFileException;
import com.github.jeromp.documentmanagementsystem.storage.StorageException;
import com.github.jeromp.documentmanagementsystem.storage.StorageProperties;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {StorageProperties.class, DocumentStorageService.class })
@TestPropertySource(properties = {"storage.files.rootPath=/FileExporer"})
@DisplayName("Document Storage Service unit test")
public class DocumentStorageServiceTest {

    @Autowired
    private DocumentStorageService service;

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
        var exception = assertThrows(DuplicateFileException.class, () -> service.create(new MockMultipartFile("test", "otherFileName.txt", MediaType.TEXT_PLAIN_VALUE,
                "Hello, other World".getBytes()), FILE_NAME));
        assertEquals(HttpStatus.FORBIDDEN, exception.getErrorCode());
    }

    @Test
    @DisplayName("save an empty file")
    void saveEmptyFile(){
        var exception = assertThrows(StorageException.class, () -> service.create(new MockMultipartFile("test", "", null ,
                (byte[]) null), FILE_NAME));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getErrorCode());
    }

    @Test
    @DisplayName("save file with directory")
    void saveFileAndDirectory(){
        var exception = assertThrows(StorageException.class, () -> service.create(TEST_FILE, "testDirectory/" + FILE_NAME));
        assertEquals(HttpStatus.FORBIDDEN, exception.getErrorCode());
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
        assertDoesNotThrow(() -> service.delete(FILE_NAME));
    }
}
