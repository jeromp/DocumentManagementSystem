package com.github.jeromp.documentmanagementsystem;

import com.github.jeromp.documentmanagementsystem.model.Document;
import com.github.jeromp.documentmanagementsystem.model.Meta;
import com.github.jeromp.documentmanagementsystem.repository.DocumentRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Document REST Api Controller Tests")
class DocumentResourceTest extends AbstractResourceTest {
    private static final String BASE_URI = "/documents/";
    private static final String EXAMPLE_TIME = "2021-08-01T12:00:00.000000";

    @Autowired
    private DocumentRepository documentRepository;

    private UUID uuid;
    private Document document;
    private Meta meta;

    @Override
    @BeforeEach
    protected void setUp() {
        super.setUp();
        this.document = new Document();
        this.document.setTitle("Example document");
        this.document.setPath("example_path/document.txt");
        this.uuid = UUID.randomUUID();
        this.document.setUuid(this.uuid);
        this.meta = new Meta();
        this.meta.setDescription("Example description is here.");
        this.meta.setDocumentCreated(LocalDateTime.parse(EXAMPLE_TIME));
        this.meta.setDocument(this.document);
        this.document.setMeta(meta);
        assertNotNull(this.document = this.documentRepository.save(document));
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
        String testDescription = "post document description";
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "hello.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes()
        );
        byte[] documentTitle = testTitle.getBytes(StandardCharsets.UTF_8);
        byte[] documentDescription = testDescription.getBytes(StandardCharsets.UTF_8);
        byte[] documentCreated = EXAMPLE_TIME.getBytes(StandardCharsets.UTF_8);
        MockPart mockTitle = new MockPart("title", documentTitle);
        MockPart mockDescription = new MockPart("description", documentDescription);
        MockPart mockDocumentCreated = new MockPart("document_created", documentCreated);
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        MvcResult mvcResult = mockMvc.perform(multipart(BASE_URI).file(file).part(mockTitle).part(mockDescription).part(mockDocumentCreated))
                .andExpect(status().isOk()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Document responseDocument = super.mapFromJson(response, Document.class);
        assertAll("all properties",
                () -> assertEquals(testTitle, responseDocument.getTitle()),
                () -> assertEquals(testDescription, responseDocument.getMeta().getDescription()),
                () -> assertEquals(LocalDateTime.parse(EXAMPLE_TIME), responseDocument.getMeta().getDocumentCreated())
        );
    }

    @Test
    @DisplayName("Test post request without meta data")
    void postDocumentWithoutMeta() throws Exception {
        String testTitle = "postDocument";
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "hello.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes()
        );
        byte[] documentTitle = "postDocument".getBytes(StandardCharsets.UTF_8);
        MockPart mockTitle = new MockPart("title", documentTitle);
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        MvcResult mvcResult = mockMvc.perform(multipart(BASE_URI).file(file).part(mockTitle))
                .andExpect(status().isOk()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Document responseDocument = super.mapFromJson(response, Document.class);
        assertAll("all properties correct",
                () -> assertEquals(testTitle, responseDocument.getTitle()),
                () -> assertNull(responseDocument.getMeta().getDescription()),
                () -> assertNull(responseDocument.getMeta().getDocumentCreated())
        );
    }

    @Test
    @DisplayName("Test post request without required fields")
    void postDocumentWithoutRequiredFields() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "hello.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes()
        );
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        MvcResult mvcResult = mockMvc.perform(multipart(BASE_URI).file(file)).andReturn();
        assertEquals(412, mvcResult.getResponse().getStatus());
    }

    @AfterEach
    void tearDown(){
        assertDoesNotThrow(() -> this.documentRepository.delete(this.document));
    }
}
