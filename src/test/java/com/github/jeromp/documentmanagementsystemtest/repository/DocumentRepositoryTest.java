package com.github.jeromp.documentmanagementsystem;

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
import com.github.jeromp.documentmanagementsystem.model.Document;
import com.github.jeromp.documentmanagementsystem.repository.DocumentRepository;

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

    private Document document1;

    @BeforeEach
    void setUp() {
        this.document1 = new Document();
        this.document1.setUuid(DOCUMENT_UUID);
        this.document1.setTitle(this.DOCUMENT_TITLE);
        this.document1.setPath(this.DOCUMENT_PATH);
        assertNotNull(this.document1 = this.repository.save(document1));
    }

    @Test
    @DisplayName("Test if there are documents in database")
    void documentsInTable(){
        assertNotNull(this.repository.findAll());
    }

    @Test
    @DisplayName("Test if inserted Document is complete")
    void findById(){
        var document2 = this.repository.findByUuid(this.document1.getUuid()).orElseThrow();
        assertAll("all properties",
                () -> assertEquals(document1.getId(), document2.getId()),
                () -> assertEquals(document1.getUuid(), document2.getUuid()),
                () -> assertEquals(document1.getTitle(), document2.getTitle()),
                () -> assertEquals(document1.getPath(), document2.getPath()),
                () -> assertEquals(document1.getCreated(), document2.getCreated())
                );
    }

    @AfterEach
    void tearDown(){
        assertDoesNotThrow(() -> this.repository.delete(this.document1));
    }

}