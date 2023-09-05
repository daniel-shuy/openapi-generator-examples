package com.github.daniel.shuy.openapi.generator.examples.spring.service;

import com.github.daniel.shuy.openapi.generator.examples.spring.dto.PatchPetDto;
import com.github.daniel.shuy.openapi.generator.examples.spring.dto.PetDto;
import com.github.daniel.shuy.openapi.generator.examples.spring.dto.PetStatusDto;
import com.github.daniel.shuy.openapi.generator.examples.spring.mapper.PetMapper;
import com.github.daniel.shuy.openapi.generator.examples.spring.model.Pet;
import com.github.daniel.shuy.openapi.generator.examples.spring.repository.PetRepository;
import com.github.daniel.shuy.openapi.generator.examples.spring.repository.TagRepository;
import com.github.daniel.shuy.openapi.generator.examples.spring.util.exception.BadRequestException;
import com.github.daniel.shuy.openapi.generator.examples.spring.util.exception.NotFoundException;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PetService {
  private final CategoryService categoryService;
  private final PetMapper petMapper;
  private final PetRepository petRepository;
  private final TagRepository tagRepository;
  private final TagService tagService;

  public Pet addPet(PetDto petDto) {
    petDto.setId(null);

    var pet = petMapper.fromDto(petDto);

    var category = categoryService.upsertCategory(petDto.getCategory());
    pet.setCategory(category);

    var tags = tagService.upsertTags(petDto.getTags());
    pet.setTags(tags);

    return petRepository.save(pet);
  }

  public void deletePet(Long petId) {
    var pet = petRepository.findById(petId)
        .orElseThrow(() -> new BadRequestException("Invalid pet value"));

    petRepository.delete(pet);
  }

  public Page<Pet> findPetsByStatus(Collection<PetStatusDto> status, Pageable pageable) {
    return petRepository.findByStatusIn(status, pageable);
  }

  public Page<Pet> findPetsByTagNames(Collection<String> tagNames, Pageable pageable) {
    if (tagNames.isEmpty()) {
      return Page.empty();
    }

    var tags = tagNames.stream()
        .map(tagRepository::findByName)
        .toList();

    tags.stream()
        .filter(Set::isEmpty)
        .findAny()
        .ifPresent(tag -> {
          throw new BadRequestException("Invalid tag value");
        });

    var uniqueTags = tags.stream()
        .flatMap(Collection::stream)
        .collect(Collectors.toSet());
    return petRepository.findByTagsIn(uniqueTags, pageable);
  }

  public Pet getPetById(Long petId) {
    if (petId == null) {
      throw new BadRequestException("Invalid ID supplied");
    }

    return petRepository.findById(petId)
        .orElseThrow(() -> new NotFoundException("Pet not found"));
  }

  public Pet patchPet(PatchPetDto patchPetDto) {
    var pet = getPetById(patchPetDto.getId());

    petMapper.updateFromDto(patchPetDto, pet);

    var category = categoryService.upsertCategory(patchPetDto.getCategory());
    pet.setCategory(category);

    patchPetDto.getTags()
        .ifPresent(tagDtos -> {
          var tags = tagService.upsertTags(tagDtos);
          pet.setTags(tags);
        });

    return petRepository.save(pet);
  }

  public Pet updatePet(PetDto petDto) {
    var pet = getPetById(petDto.getId());

    petMapper.updateFromDto(petDto, pet);

    var category = categoryService.upsertCategory(petDto.getCategory());
    pet.setCategory(category);

    var tags = tagService.upsertTags(petDto.getTags());
    pet.setTags(tags);

    return petRepository.save(pet);
  }
}
