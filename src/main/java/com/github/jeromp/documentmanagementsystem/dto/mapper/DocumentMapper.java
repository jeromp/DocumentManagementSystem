package com.github.jeromp.documentmanagementsystem.dto.mapper;

import com.github.jeromp.documentmanagementsystem.dto.DocumentDto;
import com.github.jeromp.documentmanagementsystem.dto.MetaDto;
import com.github.jeromp.documentmanagementsystem.model.Document;
import com.github.jeromp.documentmanagementsystem.model.Meta;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

@Mapper(componentModel = "spring", uses = {MetaMapper.class})
public interface DocumentMapper {
    Document documentDtoToDocument(DocumentDto documentDto);

    DocumentDto documentToDocumentDto(Document document);

    @Mapping(target = "meta.description", source = "description")
    @Mapping(target = "meta.documentCreated", source = "documentCreated")
    Document mapPartsToDocument(String title, UUID uuid, String path, String description, String documentCreated);

    @AfterMapping
    default Document establishRelation(@MappingTarget Document document) {
        var meta = document.getMeta();
        if (meta == null) {
            meta = new Meta();
            document.setMeta(meta);
        }
        meta.setDocument(document);
        document.setMeta(meta);
        return document;
    }
}
