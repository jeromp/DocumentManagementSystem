package com.github.jeromp.documentmanagementsystem.persistence.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DisplayName("Document Mapper Tests")
class DocumentMapperTest {
    private static final String TITLE = "MAPSTRUCT";
    private static final String TYPE = "FILE";
    private static final String PATH = "path_mapstruct";
    private static final String DESCRIPTION = "this is a mapstruct test";
    private static final String ISO_DATE_STRING = LocalDateTime.now().toString();

    @Autowired
    private DocumentMapper documentMapper;

    @Test
    @DisplayName("test with complete data")
    void testGivenDataToDocument() {
        var uuid = UUID.randomUUID();
        var document = assertDoesNotThrow(() -> documentMapper.mapPartsToDocument(TYPE, TITLE, uuid, PATH, DESCRIPTION, ISO_DATE_STRING));
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
        var document = assertDoesNotThrow(() -> documentMapper.mapPartsToDocument(TYPE, TITLE, uuid, PATH, null, null));
        assertAll("correct Document creation without meta",
                () -> assertEquals(TITLE, document.getTitle()),
                () -> assertEquals(PATH, document.getPath()),
                () -> assertNull(document.getMeta().getDescription()),
                () -> assertNull(document.getMeta().getDocumentCreated())
        );
    }
}
