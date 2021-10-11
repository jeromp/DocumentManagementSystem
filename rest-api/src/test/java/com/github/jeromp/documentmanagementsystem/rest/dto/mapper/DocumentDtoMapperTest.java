package com.github.jeromp.documentmanagementsystem.rest.dto.mapper;

import com.github.jeromp.documentmanagementsystem.rest.RestApiTestConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Import(RestApiTestConfig.class)
@ExtendWith(SpringExtension.class)
@DisplayName("DocumentDto Mapper Tests")
class DocumentDtoMapperTest {
    private static final String TITLE = "MAPSTRUCT";
    private static final String PATH = "path_mapstruct";
    private static final String DESCRIPTION = "this is a mapstruct test";
    private static final String ISO_DATE_STRING = LocalDateTime.now().toString();

    @Autowired
    private DocumentDtoMapper documentDtoMapper;

    @Test
    @DisplayName("given data is complete and map to documentBo ")
    void testGivenDataToDocument() {
        var uuid = UUID.randomUUID();
        var document = assertDoesNotThrow(() -> documentDtoMapper.mapPartsToDocumentBo(TITLE, DESCRIPTION, ISO_DATE_STRING));
        assertAll("correct Document creation with complete data",
                () -> assertEquals(TITLE, document.getTitle()),
                () -> assertNotNull(document.getMeta()),
                () -> assertEquals(DESCRIPTION, document.getMeta().getDescription()),
                () -> assertEquals(ISO_DATE_STRING, document.getMeta().getDocumentCreated().toString())
        );
    }

    @Test
    @DisplayName("given data is without meta and map to documentBo")
    void testWithOutMeta() {
        var uuid = UUID.randomUUID();
        var document = assertDoesNotThrow(() -> documentDtoMapper.mapPartsToDocumentBo(TITLE, null, null));
        assertAll("correct Document creation without meta",
                () -> assertEquals(TITLE, document.getTitle()),
                () -> assertNull(document.getMeta())
        );
    }
}
