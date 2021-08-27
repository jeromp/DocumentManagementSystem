package com.github.jeromp.documentmanagementsystem.dto.mapper;

import com.github.jeromp.documentmanagementsystem.dto.MetaDto;
import com.github.jeromp.documentmanagementsystem.model.Meta;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(config = MappingConfig.class)
public interface MetaMapper {
    @Mapping(target = "document", ignore = true)
    Meta metaDtoToMeta(MetaDto metaDto);

    MetaDto metaToMetaDto(Meta meta);
}
