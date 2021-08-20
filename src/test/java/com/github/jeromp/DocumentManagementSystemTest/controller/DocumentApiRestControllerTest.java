package com.github.jeromp.DocumentManagementSystem;

import com.github.jeromp.DocumentManagementSystem.AbstractApiRestControllerTest;
import com.github.jeromp.DocumentManagementSystem.model.Document;
import com.github.jeromp.DocumentManagementSystem.repository.DocumentRepository;
import com.github.jeromp.DocumentManagementSystem.storage.DocumentStorageService;
import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
class DocumentApiRestControllerTest extends AbstractApiRestControllerTest {

    @Autowired
    private DocumentRepository documentRepository;

    private DocumentStorageService documentStorageService;

    private UUID uuid;
    private Document document;

    @Override
    @BeforeEach
    protected void setUp(){
        super.setUp();
        this.document = new Document();
        this.document.setTitle("Example document");
        this.document.setPath("example_path/document.txt");
        this.uuid = UUID.randomUUID();
        this.document.setUuid(this.uuid);
        this.document = this.documentRepository.save(document);
    }

    @Test
    void getDocumentById() throws Exception {
        String uri = "/documents/" + this.uuid;
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    void postDocument(){

    }

    @AfterEach
    void tearDown(){
        this.documentRepository.delete(this.document);
    }
}
