package com.github.jeromp.DocumentManagementSystem;

import com.github.jeromp.DocumentManagementSystem.model.Document;
import com.github.jeromp.DocumentManagementSystem.model.Meta;
import com.github.jeromp.DocumentManagementSystem.repository.DocumentRepository;
import com.github.jeromp.DocumentManagementSystem.repository.MetaRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(
        replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(SpringExtension.class)
@DataJpaTest
class MetaRepositoryTest {
    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private MetaRepository metaRepository;

    private static final String META_KEY = "description";
    private static final String META_VALUE = "sample value";

    private Meta meta;

    @BeforeEach
    public void setUp(){
        this.meta = new Meta();
        this.meta.setValue(this.META_KEY);
        this.meta.setKey(this.META_VALUE);
        this.meta = this.metaRepository.save(meta);
    }

    @Test
    public void metasInTable(){
        assertNotNull(this.metaRepository.findAll());
    }

    @Test
    public void findById(){
        Meta meta2 = this.metaRepository.findById(this.meta.getId()).get();
        assertAll("all meta properties",
                () -> assertEquals(meta.getId(), meta2.getId()),
                () -> assertEquals(meta.getKey(), meta2.getKey()),
                () -> assertEquals(meta.getValue(), meta2.getValue())
        );
    }

    @AfterEach
    public void tearDown(){
        this.metaRepository.delete(this.meta);
    }
}
