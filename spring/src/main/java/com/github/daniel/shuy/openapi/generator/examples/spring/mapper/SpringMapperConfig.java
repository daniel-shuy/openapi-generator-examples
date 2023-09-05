package com.github.daniel.shuy.openapi.generator.examples.spring.mapper;

import org.mapstruct.Builder;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.MapperConfig;
import org.mapstruct.ReportingPolicy;

@MapperConfig(
    componentModel = "spring",
    builder = @Builder(disableBuilder = true),
    disableSubMappingMethodsGeneration = true,
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedSourcePolicy = ReportingPolicy.ERROR,
    unmappedTargetPolicy = ReportingPolicy.ERROR,
    uses = JsonNullableMapper.class)
public interface SpringMapperConfig {
}
