package com.github.jeromp.documentmanagementsystem.business.service;

import com.github.jeromp.documentmanagementsystem.business.port.DocumentPersistencePort;
import com.github.jeromp.documentmanagementsystem.business.service.DocumentService;
import com.github.jeromp.documentmanagementsystem.business.service.DocumentServiceException;
import com.github.jeromp.documentmanagementsystem.entity.DocumentBo;
import com.github.jeromp.documentmanagementsystem.entity.MetaBo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class DocumentServiceTest {
    private static final String EXAMPLE_TIME = "2021-08-01T12:00:00";

    @MockBean
    private DocumentPersistencePort documentPersistencePort;

    @InjectMocks
    @Resource
    private DocumentService documentService;

    private DocumentBo documentBo;

    @BeforeEach
    void setUp() {
        this.documentBo = new DocumentBo();
        this.documentBo.setTitle("Example document");
        this.documentBo.setPath("example_path/document.txt");
        var uuid = UUID.randomUUID();
        this.documentBo.setUuid(uuid);
        var metaBo = new MetaBo();
        metaBo.setDescription("Example description is here.");
        metaBo.setDocumentCreated(EXAMPLE_TIME);
        this.documentBo.setMeta(metaBo);
    }

    @Test
    @DisplayName("Test find document by correct id")
    void readByCorrectId() {
        Mockito.when(documentPersistencePort.findByUuid(this.documentBo.getUuid())).thenReturn(this.documentBo);
        var readDocument = documentService.read(this.documentBo.getUuid().toString());
        assertAll("all properties of readed document",
                () -> assertEquals(documentBo.getTitle(), readDocument.getTitle()),
                () -> assertEquals(documentBo.getUuid(), readDocument.getUuid()),
                () -> assertEquals(EXAMPLE_TIME, readDocument.getMeta().getDocumentCreated()),
                () -> assertEquals(documentBo.getMeta().getDescription(), readDocument.getMeta().getDescription())
        );
    }

    @Test
    @DisplayName("Test throw error for document with incorrect id")
    void readByIncorrectId() {
        var uuid = UUID.randomUUID();
        Mockito.when(documentPersistencePort.findByUuid(uuid)).thenThrow(new DocumentServiceException(HttpStatus.NOT_FOUND, "There will be a message."));
        var exception = assertThrows(DocumentServiceException.class, () -> documentService.read(uuid.toString()));
        assertEquals(HttpStatus.NOT_FOUND, exception.getErrorCode());
    }

    @Test
    @DisplayName("Test save document")
    void createDocument() {
        String testTitle = "postDocument";
        String testDescription = "post description";
        InputStream file = new ByteArrayInputStream("Hello, World".getBytes());

        var documentBo = new DocumentBo();
        documentBo.setTitle(testTitle);
        var metaBo = new MetaBo();
        metaBo.setDescription(testDescription);
        metaBo.setDocumentCreated(EXAMPLE_TIME);
        documentBo.setMeta(metaBo);

        Mockito.when(documentPersistencePort.save(documentBo)).thenReturn(documentBo);
        Mockito.doNothing().when(documentPersistencePort).create(any(InputStream.class), any(String.class));
        var readDocument = assertDoesNotThrow(() -> this.documentService.create(file, testTitle + ".txt", documentBo));
        assertAll("all properties of read document",
                () -> assertEquals(testTitle, readDocument.getTitle()),
                () -> assertEquals(EXAMPLE_TIME, readDocument.getMeta().getDocumentCreated()),
                () -> assertEquals(testDescription, readDocument.getMeta().getDescription())
        );
    }

    @AfterEach
    void tearDown() {
        assertDoesNotThrow(() -> this.documentPersistencePort.delete(this.documentBo));
    }
}
