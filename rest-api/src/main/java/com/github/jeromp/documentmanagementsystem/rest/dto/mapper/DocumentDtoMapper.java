package com.github.jeromp.documentmanagementsystem.rest.dto.mapper;

import com.github.jeromp.documentmanagementsystem.entity.DocumentBo;
import com.github.jeromp.documentmanagementsystem.rest.dto.DocumentDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(config = MappingConfig.class, uses = {MetaDtoMapper.class})
public interface DocumentDtoMapper {
    DocumentBo documentDtoToDocumentBo(DocumentDto documentDto);

    DocumentDto documentBoToDocumentDto(DocumentBo documentBo);

    @Mapping(target = "meta.description", source = "description")
    @Mapping(target = "meta.documentCreated", source = "documentCreated")
    @Mapping(target = "parent", ignore = true)
    @Mapping(target = "type", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "path", ignore = true)
    DocumentBo mapPartsToDocumentBo(String title, String description, String documentCreated);

    List<DocumentDto> mapDocumentBosToDocumentDtoList(List<DocumentBo> documentBos);
}
