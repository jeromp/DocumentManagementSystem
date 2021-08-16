package com.github.jeromp.DocumentManagementSystem;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import com.github.jeromp.DocumentManagementSystem.model.Document;
import com.github.jeromp.DocumentManagementSystem.repository.DocumentRepository;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(
        replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(SpringExtension.class)
@DataJpaTest
class DocumentRepositoryTest {
    @Autowired
    private DocumentRepository repository;

    private Document document1;
    private static final String DOCUMENT_TITLE = "Test Document";
    private static final String DOCUMENT_PATH = "test_path";

    @BeforeEach
    void setUp(){
        this.document1 = new Document();
        this.document1.setTitle(this.DOCUMENT_TITLE);
        this.document1.setPath(this.DOCUMENT_PATH);
        this.document1 = this.repository.save(document1);
    }

    @Test
    void documentsInTable(){
        assertNotNull(this.repository.findAll());
    }

    @Test
    void findById(){
        Document document2 = this.repository.findById(this.document1.getId()).get();
        assertAll("all properties",
                () -> assertEquals(document1.getId(), document2.getId()),
                () -> assertEquals(document1.getTitle(), document2.getTitle()),
                () -> assertEquals(document1.getPath(), document2.getPath()),
                () -> assertEquals(document1.getCreated(), document2.getCreated())
                );
    }

    @AfterEach
    void tearDown(){
        this.repository.delete(this.document1);
    }

}