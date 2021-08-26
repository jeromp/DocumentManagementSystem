package com.github.jeromp.documentmanagementsystem.dto.mapper;

import com.github.jeromp.documentmanagementsystem.dto.MetaDto;
import com.github.jeromp.documentmanagementsystem.model.Meta;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MetaMapper {

    Meta metaDtoToMeta(MetaDto metaDto);

    MetaDto metaToMetaDto(Meta meta);
}
