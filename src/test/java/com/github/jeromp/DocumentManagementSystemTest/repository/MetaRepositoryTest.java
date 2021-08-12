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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@AutoConfigureTestDatabase(
        replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(SpringExtension.class)
@DataJpaTest
public class MetaRepositoryTest {
    @Autowired
    DocumentRepository documentRepository;

    @Autowired
    MetaRepository metaRepository;

    String metaKey = "description";
    String metaValue = "sample value";

    Meta meta;

    @BeforeEach
    public void setUp(){
        this.meta = new Meta();
        this.meta.setValue(this.metaValue);
        this.meta.setKey(this.metaKey);
        this.meta = this.metaRepository.save(meta);
    }

    @Test
    public void metasInTable(){
        assertNotNull(this.metaRepository.findAll());
    }

    @Test
    public void findById(){
        Optional<Meta> optionalMeta = this.metaRepository.findById(this.meta.getId());
        Meta meta2 = optionalMeta.get();
        assertEquals(meta.getId(), meta2.getId());
    }

    @AfterEach
    public void tearDown(){
        this.metaRepository.delete(this.meta);
    }
}
