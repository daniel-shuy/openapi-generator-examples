package com.github.daniel.shuy.openapi.generator.examples.spring.mapper;

import com.github.daniel.shuy.openapi.generator.examples.spring.dto.PatchPetDto;
import com.github.daniel.shuy.openapi.generator.examples.spring.dto.PetDto;
import com.github.daniel.shuy.openapi.generator.examples.spring.dto.PetPageDto;
import com.github.daniel.shuy.openapi.generator.examples.spring.dto.TagDto;
import com.github.daniel.shuy.openapi.generator.examples.spring.model.Pet;
import com.github.daniel.shuy.openapi.generator.examples.spring.model.Tag;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.BeanMapping;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

@Mapper(config = SpringMapperConfig.class,
    uses = {
        CategoryMapper.class,
        TagMapper.class,
    })
public abstract class PetMapper {
  @Autowired
  private TagMapper tagMapper;

  @BeanMapping(ignoreUnmappedSourceProperties = {
      "category",
      "tags",
  })
  @Mapping(target = "category", ignore = true)
  @Mapping(target = "tags", ignore = true)
  public abstract Pet fromDto(PetDto petDto);

  public abstract PetDto toDto(Pet pet);

  public abstract PetPageDto toDto(Page<Pet> pet);

  @InheritConfiguration
  public abstract void updateFromDto(PetDto petDto, @MappingTarget Pet pet);

  @BeanMapping(ignoreUnmappedSourceProperties = {
      "category",
      "tags",
  })
  @Mapping(target = "category", ignore = true)
  @Mapping(target = "tags", ignore = true)
  public abstract void updateFromDto(PatchPetDto petDto, @MappingTarget Pet pet);

  protected Set<Tag> fromDtos(JsonNullable<List<TagDto>> tagDtos) {
    return tagDtos.get().stream()
        .map(tagMapper::fromDto)
        .collect(Collectors.toSet());
  }

  protected JsonNullable<List<TagDto>> toDtos(Set<Tag> tags) {
    if (tags == null) {
      return null;
    }

    var tagDtos = tags.stream()
        .map(tagMapper::toDto)
        .toList();
    return JsonNullable.of(tagDtos);
  }
}
