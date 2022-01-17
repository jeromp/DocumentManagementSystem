package com.github.jeromp.documentmanagementsystem.persistence.mapper;


import com.github.jeromp.documentmanagementsystem.entity.DocumentBo;
import com.github.jeromp.documentmanagementsystem.persistence.model.Document;
import com.github.jeromp.documentmanagementsystem.persistence.model.Meta;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.UUID;

@Mapper(config = MappingConfig.class, uses = {MetaMapper.class})
public interface DocumentMapper {
    Document documentBoToDocument(DocumentBo documentBo);

    DocumentBo documentToDocumentBo(Document document);

    @Mapping(target = "meta.description", source = "description")
    @Mapping(target = "meta.documentCreated", source = "documentCreated")
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "changed", ignore = true)
    Document mapPartsToDocument(String type, String title, UUID uuid, String path, String description, String documentCreated);

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

    List<DocumentBo> mapDocumentsToDocumentBoList(List<Document> documents);
}
