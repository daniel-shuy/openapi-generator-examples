package com.github.daniel.shuy.openapi.generator.examples.spring.mapper;

import com.github.daniel.shuy.openapi.generator.examples.spring.dto.TagDto;
import com.github.daniel.shuy.openapi.generator.examples.spring.model.Tag;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = SpringMapperConfig.class)
public interface TagMapper {
  Tag fromDto(TagDto tagDto);

  TagDto toDto(Tag tag);

  @InheritConfiguration
  void updateFromDto(TagDto tagDto, @MappingTarget Tag tag);
}
