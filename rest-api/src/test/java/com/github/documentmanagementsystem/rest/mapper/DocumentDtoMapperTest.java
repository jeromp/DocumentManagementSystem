package com.github.documentmanagementsystem.rest.mapper;

import com.github.jeromp.documentmanagementsystem.rest.dto.mapper.DocumentDtoMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@DisplayName("Document Mapper Tests")
class DocumentDtoMapperTest {
    private static final String TITLE = "MAPSTRUCT";
    private static final String PATH = "path_mapstruct";
    private static final String DESCRIPTION = "this is a mapstruct test";
    private static final String ISO_DATE_STRING = LocalDateTime.now().toString();

    @Autowired
    private DocumentDtoMapper documentDtoMapper;

    @Test
    @DisplayName("test with complete data")
    void testGivenDataToDocument() {
        var uuid = UUID.randomUUID();
        var document = assertDoesNotThrow(() -> documentDtoMapper.mapPartsToDocumentBo(TITLE, DESCRIPTION, ISO_DATE_STRING));
        assertAll("correct Document creation with complete data",
                () -> assertEquals(TITLE, document.getTitle()),
                () -> assertEquals(PATH, document.getPath()),
                () -> assertEquals(DESCRIPTION, document.getMeta().getDescription()),
                () -> assertEquals(ISO_DATE_STRING, document.getMeta().getDocumentCreated().toString())
        );
    }

    @Test
    @DisplayName("test without meta")
    void testWithOutMeta() {
        var uuid = UUID.randomUUID();
        var document = assertDoesNotThrow(() -> documentDtoMapper.mapPartsToDocumentBo(TITLE, null, null));
        assertAll("correct Document creation without meta",
                () -> assertEquals(TITLE, document.getTitle()),
                () -> assertEquals(PATH, document.getPath()),
                () -> assertNull(document.getMeta().getDescription()),
                () -> assertNull(document.getMeta().getDocumentCreated())
        );
    }
}
