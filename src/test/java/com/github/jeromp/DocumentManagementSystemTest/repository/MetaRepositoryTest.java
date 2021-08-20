package com.github.jeromp.DocumentManagementSystem;

import com.github.jeromp.DocumentManagementSystem.model.Document;
import com.github.jeromp.DocumentManagementSystem.model.Meta;
import com.github.jeromp.DocumentManagementSystem.repository.DocumentRepository;
import com.github.jeromp.DocumentManagementSystem.repository.MetaRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(
        replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(SpringExtension.class)
@DataJpaTest
@DisplayName("MetaRepositoryTest")
class MetaRepositoryTest {
    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private MetaRepository metaRepository;

    private static final String META_DESCRIPTION = "description";
    private static final LocalDateTime META_FILE_CREATED = LocalDateTime.now();

    private Meta meta;
    private Document document;

    @BeforeEach
    void setUp(){
        document = new Document();
        Document document2 = this.documentRepository.findAll().get(0);
        document.setUuid(UUID.randomUUID());
        document.setPath(document2.getPath());
        document.setTitle(document2.getTitle());
        document = this.documentRepository.save(document);
        this.meta = new Meta();
        this.meta.setDescription(this.META_DESCRIPTION);
        this.meta.setDocumentCreated(this.META_FILE_CREATED);
        this.meta.setDocument(document);
        this.meta = this.metaRepository.save(meta);
    }

    @Test
    @DisplayName("Test if meta is in table")
    void metasInTable(){
        assertNotNull(this.metaRepository.findAll());
    }

    @Test
    @DisplayName("Test if inserted Meta is complete")
    void findById(){
        var meta2 = this.metaRepository.findById(this.meta.getId()).orElseThrow();
        assertAll("all meta properties",
                () -> assertEquals(meta.getDescription(), meta2.getDescription()),
                () -> assertEquals(meta.getDocumentCreated(), meta2.getDocumentCreated()),
                () -> assertEquals(meta.getCreated(), meta2.getCreated())
        );
    }

    @AfterEach
    void tearDown(){
        this.metaRepository.delete(this.meta);
        this.documentRepository.delete(this.document);
    }
}
