package com.github.jeromp.documentmanagementsystem.persistence.repository;

import java.util.UUID;

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

    @Autowired
    private DocumentRepository repository;

    private Document persistedDocUnderTest;

    @BeforeEach
    void setUp() {
        this.persistedDocUnderTest = new Document();
        this.persistedDocUnderTest.setUuid(DOCUMENT_UUID);
        this.persistedDocUnderTest.setTitle(this.DOCUMENT_TITLE);
        this.persistedDocUnderTest.setPath(this.DOCUMENT_PATH);
        assertNotNull(this.persistedDocUnderTest = this.repository.save(persistedDocUnderTest));
    }

    @Test
    @DisplayName("Test if there are documents in database")
    void documentsInTable(){
        assertNotNull(this.repository.findAll());
    }

    @Test
    @DisplayName("Test if inserted Document is complete")
    void findById(){
        var document2 = this.repository.findByUuid(this.persistedDocUnderTest.getUuid()).orElseThrow();
        assertAll("all properties",
                () -> assertEquals(persistedDocUnderTest.getId(), document2.getId()),
                () -> assertEquals(persistedDocUnderTest.getUuid(), document2.getUuid()),
                () -> assertEquals(persistedDocUnderTest.getTitle(), document2.getTitle()),
                () -> assertEquals(persistedDocUnderTest.getPath(), document2.getPath()),
                () -> assertEquals(persistedDocUnderTest.getCreated(), document2.getCreated())
        );
    }

    @AfterEach
    void tearDown(){
        assertDoesNotThrow(() -> this.repository.delete(this.persistedDocUnderTest));
    }

}