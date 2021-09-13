package com.github.documentmanagementsystem.rest.resource;

import com.github.documentmanagementsystem.rest.RestApiTestConfig;
import com.github.jeromp.documentmanagementsystem.business.port.DocumentServicePort;
import com.github.jeromp.documentmanagementsystem.entity.DocumentBo;
import com.github.jeromp.documentmanagementsystem.entity.MetaBo;
import com.github.jeromp.documentmanagementsystem.rest.dto.DocumentDto;
import com.github.jeromp.documentmanagementsystem.rest.dto.MetaDto;
import com.github.jeromp.documentmanagementsystem.rest.dto.mapper.DocumentDtoMapper;
import com.github.jeromp.documentmanagementsystem.rest.resource.common.DocumentNotFoundException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Import(RestApiTestConfig.class)
@AutoConfigureMockMvc
@DisplayName("Document REST Api Controller Tests")
class DocumentResourceTest extends AbstractResourceTest {
    private static final String BASE_URI = "/documents/";
    private static final String EXAMPLE_TIME = "2021-08-01T12:00:00.000000";

    @MockBean
    private DocumentServicePort service;

    @MockBean
    private DocumentDtoMapper documentDtoMapper;

    private UUID uuid;
    private DocumentDto documentDto;
    private MetaDto metaDto;

    private DocumentBo documentBo;
    private MetaBo metaBo;

    @Override
    @BeforeEach
    protected void setUp() {
        super.setUp();
        this.documentDto = new DocumentDto();
        this.documentDto.setTitle("Example document");
        this.documentDto.setPath("example_path/document.txt");
        this.uuid = UUID.randomUUID();
        this.documentDto.setUuid(this.uuid);
        this.metaDto = new MetaDto();
        this.metaDto.setDescription("Example description is here.");
        this.metaDto.setDocumentCreated(EXAMPLE_TIME);
        this.documentDto.setMeta(metaDto);

        this.documentBo = new DocumentBo();
        this.documentBo.setTitle(documentDto.getTitle());
        this.documentBo.setUuid(documentDto.getUuid());
        this.documentBo.setPath(documentDto.getPath());
        this.metaBo = new MetaBo();
        this.metaBo.setDocumentCreated(this.metaDto.getDocumentCreated());
        this.metaBo.setDescription(this.metaDto.getDescription());
        this.documentBo.setMeta(this.metaBo);
    }

    @Test
    @DisplayName("Test get request with correct id")
    void getDocumentById() throws Exception {
        String uri = BASE_URI + this.uuid;
        Mockito.when(service.read(this.uuid.toString())).thenReturn(this.documentBo);
        Mockito.when(documentDtoMapper.documentBoToDocumentDto(this.documentBo)).thenReturn(this.documentDto);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        assertEquals(200, mvcResult.getResponse().getStatus());
        String response = mvcResult.getResponse().getContentAsString();
        var responseDocument = super.mapFromJson(response, DocumentDto.class);
        assertAll("all properties",
                () -> assertEquals(documentDto.getTitle(), responseDocument.getTitle()),
                () -> assertEquals(documentDto.getUuid(), responseDocument.getUuid()),
                () -> assertEquals(documentDto.getPath(), responseDocument.getPath()),
                () -> assertEquals(metaDto.getDocumentCreated(), responseDocument.getMeta().getDocumentCreated()),
                () -> assertEquals(metaDto.getDescription(), responseDocument.getMeta().getDescription())
        );
    }

    @Test
    @DisplayName("Test get request with false id")
    void getDocumentByWrongId() throws Exception {
        var randomUUID = UUID.randomUUID().toString();
        Mockito.when(service.read(randomUUID)).thenThrow(new DocumentNotFoundException(HttpStatus.NOT_FOUND, "Some message"));
        String uri = BASE_URI + randomUUID;
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

        var documentBo = new DocumentBo();
        documentBo.setTitle(testTitle);
        MetaBo metaBo = new MetaBo();
        metaBo.setDescription(testDescription);
        metaBo.setDocumentCreated(EXAMPLE_TIME);
        documentBo.setMeta(metaBo);

        var documentDto = new DocumentDto();
        documentDto.setTitle(documentBo.getTitle());
        documentDto.setUuid(documentBo.getUuid());
        documentDto.setPath(documentBo.getPath());
        var metaDto = new MetaDto();
        metaDto.setDocumentCreated(metaBo.getDocumentCreated());
        metaDto.setDescription(metaBo.getDescription());
        documentDto.setMeta(metaDto);

        Mockito.when(documentDtoMapper.mapPartsToDocumentBo(testTitle, testDescription, EXAMPLE_TIME)).thenReturn(documentBo);
        Mockito.when(service.create(any(InputStream.class), eq(file.getOriginalFilename()), any(DocumentBo.class))).thenReturn(documentBo);
        Mockito.when(documentDtoMapper.documentBoToDocumentDto(any(DocumentBo.class))).thenReturn(documentDto);

        MvcResult mvcResult = mvc.perform(multipart(BASE_URI).file(file).part(mockTitle).part(mockDescription).part(mockDocumentCreated)).andReturn();
        assertEquals(200, mvcResult.getResponse().getStatus());
        String response = mvcResult.getResponse().getContentAsString();
        var responseDocument = super.mapFromJson(response, DocumentDto.class);
        assertAll("all properties",
                () -> assertEquals(testTitle, responseDocument.getTitle()),
                () -> assertEquals(testDescription, responseDocument.getMeta().getDescription()),
                () -> assertEquals(EXAMPLE_TIME, responseDocument.getMeta().getDocumentCreated())
        );
    }

    @Test
    @DisplayName("Test post request without meta data")
    void postDocumentWithoutMeta() throws Exception {
        String testTitle = "postDocument";
        var documentBo = new DocumentBo();
        documentBo.setTitle(testTitle);
        var documentDto = new DocumentDto();
        documentDto.setTitle(testTitle);
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "hello.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes()
        );
        byte[] documentTitle = testTitle.getBytes(StandardCharsets.UTF_8);
        MockPart mockTitle = new MockPart("title", documentTitle);

        Mockito.when(documentDtoMapper.mapPartsToDocumentBo(testTitle, null, null)).thenReturn(documentBo);
        Mockito.when(service.create(any(InputStream.class), eq(file.getOriginalFilename()), any(DocumentBo.class))).thenReturn(documentBo);
        Mockito.when(documentDtoMapper.documentBoToDocumentDto(any(DocumentBo.class))).thenReturn(documentDto);

        MvcResult mvcResult = mvc.perform(multipart(BASE_URI).file(file).part(mockTitle))
                .andExpect(status().isOk()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        var responseDocument = super.mapFromJson(response, DocumentDto.class);
        assertAll("all properties correct",
                () -> assertEquals(testTitle, responseDocument.getTitle()),
                () -> assertNull(responseDocument.getMeta())
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
        // assertDoesNotThrow(() -> this.documentRepository.delete(this.document));
    }
}
