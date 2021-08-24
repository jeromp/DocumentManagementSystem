package com.github.jeromp.DocumentManagementSystem;

import com.github.jeromp.DocumentManagementSystem.controller.DocumentNotFoundException;
import com.github.jeromp.DocumentManagementSystem.model.Document;
import com.github.jeromp.DocumentManagementSystem.model.Meta;
import com.github.jeromp.DocumentManagementSystem.repository.DocumentRepository;
import com.github.jeromp.DocumentManagementSystem.service.DocumentService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("Document REST Api Controller Tests")
class DocumentServiceTest {
    @Autowired
    private DocumentService documentService;

    @Autowired
    private DocumentRepository documentRepository;

    private static final String EXAMPLE_TIME = "2021-08-01T12:00:00.000000";
    private Document document;

    @BeforeEach
    void setUp() {
        this.document = new Document();
        this.document.setTitle("Example document");
        this.document.setPath("example_path/document.txt");
        var uuid = UUID.randomUUID();
        this.document.setUuid(uuid);
        var meta = new Meta();
        meta.setDescription("Example description is here.");
        meta.setDocumentCreated(LocalDateTime.parse(EXAMPLE_TIME));
        meta.setDocument(this.document);
        this.document.setMeta(meta);
        this.document = this.documentRepository.save(document);
    }

    @Test
    @DisplayName("Test find document by correct id")
    void readByCorrectId() {
        var readDocument = documentService.read(this.document.getUuid().toString());
        assertAll("all properties of readed document",
                () -> assertEquals(document.getTitle(), readDocument.getTitle()),
                () -> assertEquals(document.getUuid(), readDocument.getUuid()),
                () -> assertEquals(document.getPath(), readDocument.getPath()),
                () -> assertEquals(document.getMeta().getDocumentCreated(), readDocument.getMeta().getDocumentCreated()),
                () -> assertEquals(document.getMeta().getDescription(), readDocument.getMeta().getDescription())
        );
    }

    @Test
    @DisplayName("Test throw error for document with incorrect id")
    void readByIncorrectId() {
        var uuid = UUID.randomUUID();
        assertThrows(DocumentNotFoundException.class, () -> documentService.read(uuid.toString()));
    }

    @Test
    @DisplayName("Test save document")
    void createDocument() {
        String testTitle = "postDocument";
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "hello.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes()
        );
        String testDescription = "post description";
        var readDocument = assertDoesNotThrow(() -> this.documentService.create(file, testTitle, testDescription, EXAMPLE_TIME));
        assertAll("all properties of readed document",
                () -> assertEquals(testTitle, readDocument.getTitle()),
                () -> assertEquals(LocalDateTime.parse(EXAMPLE_TIME), readDocument.getMeta().getDocumentCreated()),
                () -> assertEquals(testDescription, readDocument.getMeta().getDescription())
        );
    }

    @AfterEach
    void tearDown() {
        this.documentRepository.delete(this.document);
    }
}
