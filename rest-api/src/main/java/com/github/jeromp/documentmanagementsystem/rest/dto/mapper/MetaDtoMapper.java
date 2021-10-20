package com.github.jeromp.documentmanagementsystem.rest.dto.mapper;

import com.github.jeromp.documentmanagementsystem.entity.MetaBo;
import com.github.jeromp.documentmanagementsystem.rest.dto.MetaDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MappingConfig.class)
public interface MetaDtoMapper {
    MetaBo metaDtoToMetaBo(MetaDto metaDto);

    MetaDto metaBoToMetaDto(MetaBo meta);
}
