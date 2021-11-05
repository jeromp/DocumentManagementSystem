package com.github.jeromp.documentmanagementsystem.rest.dto.mapper;

import com.github.jeromp.documentmanagementsystem.entity.DocumentBo;
import com.github.jeromp.documentmanagementsystem.rest.dto.DocumentDto;
import org.mapstruct.*;

import java.util.List;
import java.util.UUID;

@Mapper(config = MappingConfig.class, uses = {MetaDtoMapper.class})
public interface DocumentDtoMapper {
    DocumentBo documentDtoToDocumentBo(DocumentDto documentDto);

    DocumentDto documentBoToDocumentDto(DocumentBo documentBo);

    @Mapping(target = "meta.description", source = "description")
    @Mapping(target = "meta.documentCreated", source = "documentCreated")
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "path", ignore = true)
    DocumentBo mapPartsToDocumentBo(String title, String description, String documentCreated);

    List<DocumentDto> mapDocumentBosToDocumentDtoList(List<DocumentBo> documentBos);
}
