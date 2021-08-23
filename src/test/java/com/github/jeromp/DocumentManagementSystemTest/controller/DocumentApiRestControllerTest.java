package com.github.jeromp.DocumentManagementSystem;

import com.github.jeromp.DocumentManagementSystem.AbstractApiRestControllerTest;
import com.github.jeromp.DocumentManagementSystem.model.Document;
import com.github.jeromp.DocumentManagementSystem.model.Meta;
import com.github.jeromp.DocumentManagementSystem.repository.DocumentRepository;
import com.github.jeromp.DocumentManagementSystem.repository.MetaRepository;
import com.github.jeromp.DocumentManagementSystem.storage.DocumentStorageService;
import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.Part;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Document REST Api Controller Tests")
class DocumentApiRestControllerTest extends AbstractApiRestControllerTest {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private MetaRepository metaRepository;

    private DocumentStorageService documentStorageService;

    private static final String BASE_URI = "/documents/";
    private static final String EXAMPLE_TIME = "2021-08-01T12:00:00.000000";
    private UUID uuid;
    private Document document;
    private Meta meta;

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
        this.meta = new Meta();
        this.meta.setDescription("Example description is here.");
        this.meta.setDocumentCreated(LocalDateTime.parse(EXAMPLE_TIME));
        this.meta.setDocument(document);
        this.meta = this.metaRepository.save(meta);
    }

    @Test
    @DisplayName("Test get request with correct id")
    void getDocumentById() throws Exception {
        String uri = BASE_URI + this.uuid;
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        assertEquals(200, mvcResult.getResponse().getStatus());
        String response = mvcResult.getResponse().getContentAsString();
        Document responseDocument = super.mapFromJson(response, Document.class);
        assertAll("all properties",
                () -> assertEquals(document.getTitle(), responseDocument.getTitle()),
                () -> assertEquals(document.getUuid(), responseDocument.getUuid()),
                () -> assertEquals(document.getPath(), responseDocument.getPath()),
                () -> assertEquals(meta.getDocumentCreated(), responseDocument.getMeta().getDocumentCreated()),
                () -> assertEquals(meta.getDescription(), responseDocument.getMeta().getDescription())
        );
    }

    @Test
    @DisplayName("Test get request with false id")
    void getDocumentByWrongId() throws Exception {
        String uri = BASE_URI + UUID.randomUUID();
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        assertEquals(404, mvcResult.getResponse().getStatus());
    }


    @Test
    @DisplayName("Test post request")
    void postDocument() throws Exception {
        String testTitle = "postDocument";
        MockMultipartFile file
                = new MockMultipartFile(
                "file",
                "hello.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes()
        );
        byte[] documentTitle = "postDocument".getBytes(StandardCharsets.UTF_8);
        MockPart mockTitle = new MockPart("title", documentTitle);
        MockMvc mockMvc
                = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        MvcResult mvcResult = mockMvc.perform(multipart(BASE_URI).file(file).part(mockTitle))
                .andExpect(status().isOk()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Document responseDocument = super.mapFromJson(response, Document.class);
        assertAll("all properties",
                () -> assertEquals(testTitle, responseDocument.getTitle())
        );
    }

    @Test
    @DisplayName("Test post request without required fields")
    void postDocumentWithoutRequiredFields() throws Exception {
        String testTitle = "postDocument";
        MockMultipartFile file
                = new MockMultipartFile(
                "file",
                "hello.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes()
        );
        MockMvc mockMvc
                = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        MvcResult mvcResult = mockMvc.perform(multipart(BASE_URI).file(file)).andReturn();
        assertEquals(400, mvcResult.getResponse().getStatus());
    }

    @AfterEach
    void tearDown(){
        this.metaRepository.delete(this.meta);
        this.documentRepository.delete(this.document);
    }
}
