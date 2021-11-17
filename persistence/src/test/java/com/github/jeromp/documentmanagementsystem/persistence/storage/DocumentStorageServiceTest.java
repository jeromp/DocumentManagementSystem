package com.github.jeromp.documentmanagementsystem.persistence.storage;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {StorageProperties.class, DocumentStorageService.class})
@TestPropertySource(properties = {"storage.files.rootPath=/FileExporer"})
@DisplayName("Document Storage Service unit test")
public class DocumentStorageServiceTest {
    private static final String FILE_NAME = "test.txt";
    private static final String FILE_CONTENT = "Hello, World";
    private static final InputStream TEST_FILE = new ByteArrayInputStream(FILE_CONTENT.getBytes());
    private static final String TEST_FILE_NAME = "otherFileName.txt";

    @Autowired
    private DocumentStorageService service;

    @Test
    @DisplayName("load non existent file")
    void loadNonExistent() {
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
        var exception = assertThrows(DuplicateFileException.class, () -> service.create(new ByteArrayInputStream("Hello, other World".getBytes()), FILE_NAME));
        assertEquals(HttpStatus.FORBIDDEN, exception.getErrorCode());
    }

    @Test
    @DisplayName("save file with directory")
    void saveFileAndDirectory(){
        var exception = assertThrows(StorageException.class, () -> service.create(TEST_FILE, "testDirectory/" + FILE_NAME));
        assertEquals(HttpStatus.FORBIDDEN, exception.getErrorCode());
    }

    @Test
    @DisplayName("delete existing file")
    void deleteExistingFile() {
        service.create(TEST_FILE, FILE_NAME);
        assertTrue(Files.exists(service.read(FILE_NAME)));
        assertTrue(service.delete(FILE_NAME));
        assertFalse(Files.exists(service.read(FILE_NAME)));
    }

    @Test
    @DisplayName("delete not existing file")
    void deleteNotExistingFile() {
        String randomFileName = "randomStringFileName";
        assertFalse(Files.exists(service.read(randomFileName)));
        assertFalse(service.delete(randomFileName));
    }

    @Test
    @DisplayName("load file as resource")
    void loadExistingFileAsResource() {
        assertDoesNotThrow(() -> service.create(TEST_FILE, FILE_NAME));
        var resource = assertDoesNotThrow(() -> service.readAsResource(FILE_NAME));
        assertAll("file properties",
                () -> assertTrue(resource.exists()),
                () -> assertTrue(resource.isFile())
        );
    }

    @Test
    @DisplayName("throw error if file not exists for trying to load as resource")
    void loadNotExistingFileAsResource() {
        var exception = assertThrows(StorageException.class, () -> service.readAsResource("something_else"));
        assertEquals(HttpStatus.NOT_FOUND, exception.getErrorCode());
    }

    @Test
    @DisplayName("load file as input stream")
    void loadExistingFileAsInputStream() {
        assertDoesNotThrow(() -> service.create(TEST_FILE, FILE_NAME));
        assertDoesNotThrow(() -> service.readAsInputStream(FILE_NAME));
    }

    @Test
    @DisplayName("throw error if file not exists for trying to load as input stream")
    void loadNotExistingFileAsInputStream() {
        var exception = assertThrows(StorageException.class, () -> service.readAsInputStream("something_else"));
        assertEquals(HttpStatus.NOT_FOUND, exception.getErrorCode());
    }

    @AfterEach
    void tearDown() {
        assertDoesNotThrow(() -> service.delete(FILE_NAME));
    }
}
