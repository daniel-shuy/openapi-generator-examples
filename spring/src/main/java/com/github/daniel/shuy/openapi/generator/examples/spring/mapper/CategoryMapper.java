package com.github.daniel.shuy.openapi.generator.examples.spring.mapper;

import com.github.daniel.shuy.openapi.generator.examples.spring.dto.CategoryDto;
import com.github.daniel.shuy.openapi.generator.examples.spring.model.Category;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = SpringMapperConfig.class)
public interface CategoryMapper {
  Category fromDto(CategoryDto categoryDto);

  CategoryDto toDto(Category category);

  @InheritConfiguration
  void updateFromDto(CategoryDto categoryDto, @MappingTarget Category category);
}
