package com.github.jeromp.documentmanagementsystem.business.service;

import com.github.jeromp.documentmanagementsystem.business.port.DocumentDataPersistencePort;
import com.github.jeromp.documentmanagementsystem.business.port.DocumentFilePersistencePort;
import com.github.jeromp.documentmanagementsystem.business.service.common.DocumentNotFoundException;
import com.github.jeromp.documentmanagementsystem.business.service.common.DocumentServiceException;
import com.github.jeromp.documentmanagementsystem.entity.DocumentBo;
import com.github.jeromp.documentmanagementsystem.entity.MetaBo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;

@ExtendWith(SpringExtension.class)
class DocumentServiceTest {
    private static final String EXAMPLE_TIME = "2021-08-01T12:00:00";
    private static final String DOCUMENT_TITLE = "Example document";
    private static final String DOCUMENT_DESCRIPTION = "Example description is here.";

    @Mock
    private DocumentDataPersistencePort documentDataPersistencePort;

    @Mock
    private DocumentFilePersistencePort documentFilePersistencePort;

    @InjectMocks
    private DocumentService documentService;

    private DocumentBo documentBo;

    @Mock
    private Resource documentResource;

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
        when(documentDataPersistencePort.readByUuid(this.documentBo.getUuid())).thenReturn(this.documentBo);
        var readDocument = documentService.readBo(this.documentBo.getUuid().toString());
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
        when(documentDataPersistencePort.readByUuid(uuid)).thenThrow(new DocumentServiceException(HttpStatus.NOT_FOUND, "There will be a message."));
        var exception = assertThrows(DocumentServiceException.class, () -> documentService.readBo(uuid.toString()));
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

        when(documentDataPersistencePort.create(documentBo)).thenReturn(documentBo);
        doNothing().when(documentFilePersistencePort).create(any(InputStream.class), any(String.class));
        var readDocument = assertDoesNotThrow(() -> this.documentService.create(file, testTitle + ".txt", documentBo));
        assertAll("all properties of read document",
                () -> assertEquals(testTitle, readDocument.getTitle()),
                () -> assertEquals(EXAMPLE_TIME, readDocument.getMeta().getDocumentCreated()),
                () -> assertEquals(testDescription, readDocument.getMeta().getDescription())
        );
    }

    @Test
    @DisplayName("Test find documents by query")
    void readByQuery() {
        String title = DOCUMENT_TITLE;
        String description = DOCUMENT_DESCRIPTION;
        var documentBoList = new ArrayList<DocumentBo>();
        documentBoList.add(this.documentBo);
        when(documentDataPersistencePort.findByQuery(title, description, null, null)).thenReturn(documentBoList);
        var queriedDocuments = documentService.findByQuery(Optional.of(title), Optional.of(description), Optional.ofNullable(null), Optional.ofNullable(null));
        assertAll("all properties of readed document",
                () -> assertEquals(1, queriedDocuments.size()),
                () -> assertEquals(documentBo.getUuid(), queriedDocuments.get(0).getUuid())
        );
    }

    @Test
    @DisplayName("Test load document file as resource")
    void readAsResource() {
        when(documentDataPersistencePort.readByUuid(this.documentBo.getUuid())).thenReturn(this.documentBo);
        when(documentFilePersistencePort.readAsResource(this.documentBo.getPath())).thenReturn(this.documentResource);
        when(this.documentResource.exists()).thenReturn(true);
        when(this.documentResource.isFile()).thenReturn(true);
        var readDocument = documentService.readResource(this.documentBo.getUuid().toString());
        assertAll("all properties of readed document",
                () -> assertEquals(readDocument, readDocument),
                () -> assertTrue(readDocument.exists()),
                () -> assertTrue(readDocument.isFile())
        );
    }

    @Test
    @DisplayName("Test load document file as stream")
    void readAsStream() {
        var content = "Hello World".getBytes();
        var mimeType = "application/pdf";
        var stream = new ByteArrayInputStream(content);
        when(documentDataPersistencePort.readByUuid(this.documentBo.getUuid())).thenReturn(this.documentBo);
        when(documentFilePersistencePort.readAsInputStream(this.documentBo.getPath())).thenReturn(stream);
        when(documentFilePersistencePort.readMimeType(this.documentBo.getPath())).thenReturn(mimeType);
        when(this.documentResource.exists()).thenReturn(true);
        when(this.documentResource.isFile()).thenReturn(true);
        var readDocument = documentService.readStream(this.documentBo.getUuid().toString());
        assertAll("all properties of readed document stream bo",
                () -> assertDoesNotThrow(() -> readDocument.getBytes()),
                () -> assertEquals(content.length, readDocument.getBytes().length),
                () -> assertEquals(mimeType, readDocument.getContentType())
        );
    }

    @Test
    @DisplayName("Test delete document")
    void deleteDocument() {
        when(documentDataPersistencePort.readByUuid(this.documentBo.getUuid())).thenReturn(this.documentBo);
        when(documentFilePersistencePort.delete(this.documentBo.getPath())).thenReturn(true);
        doNothing().when(documentDataPersistencePort).delete(this.documentBo.getUuid());
        assertDoesNotThrow(() -> documentService.delete(this.documentBo.getUuid().toString()));
    }

    @Test
    @DisplayName("Test delete not existing document")
    void deleteNotExistingDocument() {
        var uuid = UUID.randomUUID();
        when(documentDataPersistencePort.readByUuid(uuid)).thenThrow(DocumentNotFoundException.class);
        assertThrows(DocumentNotFoundException.class, () -> documentService.delete(uuid.toString()));
    }

}
