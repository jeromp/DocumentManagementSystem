package com.github.jeromp.documentmanagementsystem.persistence.mapper;

import com.github.jeromp.documentmanagementsystem.entity.BaseBo;
import com.github.jeromp.documentmanagementsystem.persistence.model.BaseEntity;
import org.mapstruct.MapperConfig;
import org.mapstruct.Mapping;
import org.mapstruct.MappingInheritanceStrategy;
import org.mapstruct.ReportingPolicy;

@MapperConfig(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_FROM_CONFIG
)
public interface MappingConfig {
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "changed", ignore = true)
    BaseEntity anyDtoToEntity(Object dto);
}
