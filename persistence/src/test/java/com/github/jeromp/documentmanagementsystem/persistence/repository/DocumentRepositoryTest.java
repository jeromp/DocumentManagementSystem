package com.github.jeromp.documentmanagementsystem.persistence.repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

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


    @Autowired
    private DocumentRepository repository;

    private LocalDateTime YESTERDAY_DATE = LocalDateTime.parse("2021-11-01T12:00:00");
    private Document persistedDocUnderTest;
    private Meta persistedMetaUnderTest;

    @BeforeEach
    void setUp() {
        this.persistedDocUnderTest = new Document();
        this.persistedDocUnderTest.setUuid(DOCUMENT_UUID);
        this.persistedDocUnderTest.setTitle(this.DOCUMENT_TITLE);
        this.persistedDocUnderTest.setPath(this.DOCUMENT_PATH);
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
                () -> assertEquals(persistedDocUnderTest.getCreated(), document2.getCreated())
        );
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
    @DisplayName("Get Documents by Query with date parameters")
    void findByQueryWithDate() {
        Optional<String> optionalNull = Optional.ofNullable(null);
        var documentList = this.repository.findByQuery(null, null, YESTERDAY_DATE.minusDays(1), YESTERDAY_DATE.plusDays(1));
        var documentList2 = this.repository.findByQuery(null, null, YESTERDAY_DATE, YESTERDAY_DATE.plusDays(1));
        var documentList3 = this.repository.findByQuery(null, null, YESTERDAY_DATE.minusDays(1), YESTERDAY_DATE);
        var documentList4 = this.repository.findByQuery(null, null, YESTERDAY_DATE, YESTERDAY_DATE);
        var documentList5 = this.repository.findByQuery(null, null, LocalDateTime.now(), null);
        System.out.println(documentList.get(0).getMeta().getDocumentCreated());
        System.out.println(YESTERDAY_DATE);
        System.out.println(documentList.get(0).getMeta().getDocumentCreated() == YESTERDAY_DATE);
        assertAll("different date ranges",
                () -> assertEquals(1, documentList.size()),
                () -> assertEquals(1, documentList2.size()),
                () -> assertEquals(1, documentList3.size()),
                () -> assertEquals(1, documentList4.size()),
                () -> assertEquals(0, documentList5.size())
        );

    }

    @Test
    @DisplayName("Get Empty List of Documents after Query")
    void findEmptyListByQuery() {
        var documentList = this.repository.findByQuery("WRONG_TITLE", null, null, null);
        var documentList2 = this.repository.findByQuery(null, "WRONG Description.", null, null);
        assertEquals(0, documentList.size());
        assertEquals(0, documentList2.size());
    }

    @AfterEach
    void tearDown() {
        assertDoesNotThrow(() -> this.repository.delete(this.persistedDocUnderTest));
    }

}