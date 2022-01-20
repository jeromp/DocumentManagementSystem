package com.github.jeromp.documentmanagementsystem.persistence.repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import com.github.jeromp.documentmanagementsystem.persistence.model.DocumentType;
import com.github.jeromp.documentmanagementsystem.persistence.model.IllegalRelationshipException;
import com.github.jeromp.documentmanagementsystem.persistence.model.Meta;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import com.github.jeromp.documentmanagementsystem.persistence.model.Document;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(
        replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(SpringExtension.class)
@DataJpaTest
@DisplayName("DocumentRepositoryTest")
class DocumentRepositoryTest {
    private static final String DOCUMENT_TITLE = "Test Document";
    private static final String DOCUMENT_PATH = "test_path";
    private static final UUID DOCUMENT_UUID = UUID.randomUUID();
    private static final String META_DESCRIPTION = "This is a description.";
    private static final String DIRECTORY_TITLE = "Test Directory";
    private static final String DIRECTORY_PATH = "/";
    private static final UUID DIRECTORY_UUID = UUID.randomUUID();


    @Autowired
    private DocumentRepository repository;

    private LocalDateTime YESTERDAY_DATE = LocalDateTime.parse("2021-11-01T12:00:00");
    private Document persistedDirectoryUnderTest;
    private Document persistedDocUnderTest;
    private Meta persistedMetaUnderTest;

    @BeforeEach
    void setUp() {
        this.persistedDirectoryUnderTest = new Document();
        this.persistedDirectoryUnderTest.setUuid(DIRECTORY_UUID);
        this.persistedDirectoryUnderTest.setTitle(DIRECTORY_TITLE);
        this.persistedDirectoryUnderTest.setPath(DIRECTORY_PATH);
        this.persistedDirectoryUnderTest.setType(DocumentType.DIRECTORY);
        assertNotNull(this.persistedDirectoryUnderTest = this.repository.save(persistedDirectoryUnderTest));
        this.persistedDocUnderTest = new Document();
        this.persistedDocUnderTest.setUuid(DOCUMENT_UUID);
        this.persistedDocUnderTest.setTitle(this.DOCUMENT_TITLE);
        this.persistedDocUnderTest.setPath(this.DOCUMENT_PATH);
        this.persistedDocUnderTest.setType(DocumentType.FILE);
        this.persistedDocUnderTest.setParent(this.persistedDirectoryUnderTest);
        this.persistedMetaUnderTest = new Meta();
        this.persistedMetaUnderTest.setDescription(META_DESCRIPTION);
        this.persistedMetaUnderTest.setDocumentCreated(YESTERDAY_DATE);
        this.persistedMetaUnderTest.setDocument(this.persistedDocUnderTest);
        this.persistedDocUnderTest.setMeta(this.persistedMetaUnderTest);
        assertNotNull(this.persistedDocUnderTest = this.repository.save(persistedDocUnderTest));
    }

    @Test
    @DisplayName("Test if there are documents in database")
    void documentsInTable(){
        assertNotNull(this.repository.findAll());
    }

    @Test
    @DisplayName("Test if inserted Document is complete")
    void findById() {
        var document2 = this.repository.findByUuid(this.persistedDocUnderTest.getUuid()).orElseThrow();
        assertAll("all properties",
                () -> assertEquals(persistedDocUnderTest.getId(), document2.getId()),
                () -> assertEquals(persistedDocUnderTest.getUuid(), document2.getUuid()),
                () -> assertEquals(persistedDocUnderTest.getTitle(), document2.getTitle()),
                () -> assertEquals(persistedDocUnderTest.getPath(), document2.getPath()),
                () -> assertEquals(persistedDocUnderTest.getCreated(), document2.getCreated()),
                () -> assertEquals(persistedDocUnderTest.getType(), document2.getType()),
                () -> assertEquals(persistedDirectoryUnderTest, document2.getParent())
        );
    }

    @Test
    @DisplayName("Test if inserted Directory is complete and has files")
    void findDirectoryById() {
        var directory = this.repository.findByUuid(this.persistedDirectoryUnderTest.getUuid()).orElseThrow();
        assertAll("all properties",
                () -> assertEquals(persistedDirectoryUnderTest.getId(), directory.getId()),
                () -> assertEquals(persistedDirectoryUnderTest.getUuid(), directory.getUuid()),
                () -> assertEquals(persistedDirectoryUnderTest.getTitle(), directory.getTitle()),
                () -> assertEquals(persistedDirectoryUnderTest.getPath(), directory.getPath()),
                () -> assertEquals(persistedDirectoryUnderTest.getCreated(), directory.getCreated()),
                () -> assertEquals(persistedDirectoryUnderTest.getType(), directory.getType()),
                () -> assertEquals(1, directory.getChildren().size())
        );
    }

    @Test
    @DisplayName("Add new Document to existing directory")
    void addNewDocumentToDirectory() {
        var document = new Document();
        var uuid = UUID.randomUUID();
        document.setUuid(uuid);
        document.setType(DocumentType.FILE);
        document.setTitle("New test document");
        document.setParent(this.persistedDirectoryUnderTest);
        assertDoesNotThrow(() -> this.repository.save(document));
        var directory = assertDoesNotThrow(() -> this.repository.findByUuid(persistedDirectoryUnderTest.getUuid()).orElseThrow());
        assertEquals(2, directory.getChildren().size());
        assertDoesNotThrow(() -> this.repository.delete(document));
    }

    @Test
    @DisplayName("Add new Document to a document")
    void addParentNotPermitted() {
        var document = new Document();
        assertThrows(IllegalRelationshipException.class, () -> document.setParent(this.persistedDocUnderTest));
    }

    @Test
    @DisplayName("Get Documents by Query with title")
    void findByQueryWithTitle() {
        var documentList = this.repository.findByQuery(DOCUMENT_TITLE, null, null, null);
        assertAll("",
                () -> assertEquals(1, documentList.size()),
                () -> assertEquals(DOCUMENT_TITLE, documentList.get(0).getTitle())
        );
    }

    @Test
    @DisplayName("Get Documents by Query with title and description")
    void findByQueryWithTitleAndDescription() {
        var documentList = this.repository.findByQuery(DOCUMENT_TITLE, META_DESCRIPTION, null, null);
        assertAll("",
                () -> assertEquals(1, documentList.size()),
                () -> assertEquals(DOCUMENT_TITLE, documentList.get(0).getTitle()),
                () -> assertEquals(META_DESCRIPTION, documentList.get(0).getMeta().getDescription())
        );
    }

    @Test
    @DisplayName("Get Documents by Query with description")
    void findByQueryWithDescription() {
        var documentList = this.repository.findByQuery(null, META_DESCRIPTION, null, null);
        var documentList2 = this.repository.findByQuery(null, "DESCRIPTION", null, null);
        assertAll("",
                () -> assertEquals(1, documentList.size()),
                () -> assertEquals(1, documentList2.size()),
                () -> assertEquals(META_DESCRIPTION, documentList.get(0).getMeta().getDescription())
        );
    }

    @Test
    @DisplayName("Get Documents by Query with regular date parameters")
    void findByQueryWithRegularDateRange() {
        var documentList = assertDoesNotThrow(() -> this.repository.findByQuery(null, null, YESTERDAY_DATE.minusDays(1), YESTERDAY_DATE.plusDays(1)));
        assertEquals(1, documentList.size());
    }

    @Test
    @DisplayName("Get Documents by Query with edge case start date parameter")
    void findByQueryWithEdgeCaseStartDate() {
        var documentList = assertDoesNotThrow(() -> this.repository.findByQuery(null, null, YESTERDAY_DATE, YESTERDAY_DATE.plusDays(1)));
        assertEquals(1, documentList.size());
    }

    @Test
    @DisplayName("Get Documents by Query with edge case end date parameter")
    void findByQueryWithEdgeCaseEndDate() {
        var documentList = assertDoesNotThrow(() -> this.repository.findByQuery(null, null, YESTERDAY_DATE.minusDays(1), YESTERDAY_DATE));
        assertEquals(1, documentList.size());
    }

    @Test
    @DisplayName("Get Documents by Query with edge case start and end date parameter")
    void findByQueryWithEdgeCaseStartAndEndDate() {
        var documentList = assertDoesNotThrow(() -> this.repository.findByQuery(null, null, YESTERDAY_DATE, YESTERDAY_DATE));
        assertEquals(1, documentList.size());
    }

    @Test
    @DisplayName("Get Documents by Query outside range date parameter")
    void findByQueryWithOutsideDateRange() {
        var documentList = assertDoesNotThrow(() -> this.repository.findByQuery(null, null, LocalDateTime.now(), null));
        assertEquals(0, documentList.size());
    }

    @Test
    @DisplayName("Get Empty List of Documents after Query")
    void findEmptyListByQuery() {
        var documentList = this.repository.findByQuery("WRONG_TITLE", null, null, null);
        var documentList2 = this.repository.findByQuery(null, "WRONG Description.", null, null);
        assertEquals(0, documentList.size());
        assertEquals(0, documentList2.size());
    }

    @Test
    @DisplayName("Find and delete document")
    void findAndDelete() {
        var result = this.repository.findByUuid(this.persistedDocUnderTest.getUuid());
        assertTrue(result.isPresent());
        var document = result.orElse(null);
        assertDoesNotThrow(() -> this.repository.delete(document));
        assertFalse(this.repository.findByUuid(this.persistedDocUnderTest.getUuid()).isPresent());
    }

    @AfterEach
    void tearDown() {
        assertDoesNotThrow(() -> this.repository.delete(this.persistedDocUnderTest));
        assertDoesNotThrow(() -> this.repository.delete(this.persistedDirectoryUnderTest));
    }

}