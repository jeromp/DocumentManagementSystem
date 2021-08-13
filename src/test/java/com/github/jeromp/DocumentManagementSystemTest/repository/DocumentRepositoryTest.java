package com.github.jeromp.DocumentManagementSystem;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import com.github.jeromp.DocumentManagementSystem.model.Document;
import com.github.jeromp.DocumentManagementSystem.repository.DocumentRepository;

@AutoConfigureTestDatabase(
        replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(SpringExtension.class)
@DataJpaTest
public class DocumentRepositoryTest {
    @Autowired
    private DocumentRepository repository;

    private Document document1;
    private String documentTitle = "Test Document";
    private String documentPath = "test_path";

    @BeforeEach
    public void setUp(){
        this.document1 = new Document();
        this.document1.setTitle(this.documentTitle);
        this.document1.setPath(this.documentPath);
        this.document1 = this.repository.save(document1);
    }

    @Test
    public void documentsInTable(){
        assertNotNull(this.repository.findAll());
    }

    @Test
    public void findById(){
        Optional<Document> optionalDocument = this.repository.findById(this.document1.getId());
        Document document2 = optionalDocument.get();
        assertEquals(document1.getId(), document2.getId());
    }

    @AfterEach
    public void tearDown(){
        this.repository.delete(this.document1);
    }

}