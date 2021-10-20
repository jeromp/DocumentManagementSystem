package com.github.jeromp.documentmanagementsystem.persistence.mapper;

import com.github.jeromp.documentmanagementsystem.entity.MetaBo;
import com.github.jeromp.documentmanagementsystem.persistence.model.Meta;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MappingConfig.class)
public interface MetaMapper {
    @Mapping(target = "document", ignore = true)
    Meta metaBoToMeta(MetaBo metaBo);

    MetaBo metaToMetaBo(Meta meta);
}
