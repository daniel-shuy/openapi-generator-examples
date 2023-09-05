package com.github.daniel.shuy.openapi.generator.examples.spring.service;

import com.github.daniel.shuy.openapi.generator.examples.spring.dto.CategoryDto;
import com.github.daniel.shuy.openapi.generator.examples.spring.mapper.CategoryMapper;
import com.github.daniel.shuy.openapi.generator.examples.spring.model.Category;
import com.github.daniel.shuy.openapi.generator.examples.spring.repository.CategoryRepository;
import com.github.daniel.shuy.openapi.generator.examples.spring.util.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {
  private final CategoryMapper categoryMapper;
  private final CategoryRepository categoryRepository;

  public Category upsertCategory(CategoryDto categoryDto) {
    if (categoryDto == null) {
      return null;
    }

    var categoryId = categoryDto.getId();
    return categoryId != null
        ? updateCategory(categoryDto)
        : addCategory(categoryDto);
  }


  public Category addCategory(CategoryDto categoryDto) {
    var category = categoryMapper.fromDto(categoryDto);
    return categoryRepository.save(category);
  }

  public Category updateCategory(CategoryDto categoryDto) {
    var category = getCategoryById(categoryDto.getId());
    categoryMapper.updateFromDto(categoryDto, category);
    return categoryRepository.save(category);
  }

  public Category getCategoryById(Long categoryId) {
    return categoryRepository.findById(categoryId)
        .orElseThrow(() -> new NotFoundException("Category not found"));
  }
}
