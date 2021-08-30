package com.github.jeromp.documentmanagementsystem.dto.mapper;

import com.github.jeromp.documentmanagementsystem.model.BaseEntity;
import org.mapstruct.MapperConfig;
import org.mapstruct.Mapping;
import org.mapstruct.MappingInheritanceStrategy;
import org.mapstruct.ReportingPolicy;

@MapperConfig(
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_FROM_CONFIG
)
public interface MappingConfig {
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "changed", ignore = true)
    BaseEntity anyDtoToEntity(Object dto);
}
