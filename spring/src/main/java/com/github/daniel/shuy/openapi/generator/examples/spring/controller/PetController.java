package com.github.daniel.shuy.openapi.generator.examples.spring.controller;

import com.github.daniel.shuy.openapi.generator.examples.spring.api.PetApi;
import com.github.daniel.shuy.openapi.generator.examples.spring.dto.ApiResponseDto;
import com.github.daniel.shuy.openapi.generator.examples.spring.dto.PatchPetDto;
import com.github.daniel.shuy.openapi.generator.examples.spring.dto.PetDto;
import com.github.daniel.shuy.openapi.generator.examples.spring.dto.PetPageDto;
import com.github.daniel.shuy.openapi.generator.examples.spring.dto.PetStatusDto;
import com.github.daniel.shuy.openapi.generator.examples.spring.mapper.PetMapper;
import com.github.daniel.shuy.openapi.generator.examples.spring.service.PetService;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PetController implements PetApi {
  private final PetMapper petMapper;
  private final PetService petService;

  @Override
  public ResponseEntity<PetDto> addPet(PetDto petDto) {
    var pet = petService.addPet(petDto);
    return ResponseEntity
        .ok(petMapper.toDto(pet));
  }

  @Override
  public ResponseEntity<Void> deletePet(Long petId) {
    petService.deletePet(petId);
    return ResponseEntity
        .noContent()
        .build();
  }

  @Override
  public ResponseEntity<PetPageDto> findPetsByStatus(List<PetStatusDto> status,
                                                     Integer page, Integer size, List<String> sort, // TODO: remove after https://github.com/OpenAPITools/openapi-generator/pull/14064 is merged and released
                                                     Pageable pageable) {
    var pets = petService.findPetsByStatus(status, pageable);
    return ResponseEntity
        .ok(petMapper.toDto(pets));
  }

  @Override
  public ResponseEntity<PetPageDto> findPetsByTags(List<String> tags,
                                                   Integer page, Integer size, List<String> sort, // TODO: remove after https://github.com/OpenAPITools/openapi-generator/pull/14064 is merged and released
                                                   Pageable pageable) {
    var pets = petService.findPetsByTagNames(tags, pageable);
    return ResponseEntity
        .ok(petMapper.toDto(pets));
  }

  @Override
  public ResponseEntity<PetDto> getPetById(Long petId) {
    var pet = petService.getPetById(petId);
    return ResponseEntity
        .ok(petMapper.toDto(pet));
  }

  @Override
  public ResponseEntity<PetDto> patchPet(PatchPetDto petDto) {
    var pet = petService.patchPet(petDto);
    return ResponseEntity
        .ok(petMapper.toDto(pet));
  }

  @Override
  public ResponseEntity<PetDto> updatePet(PetDto petDto) {
    var pet = petService.updatePet(petDto);
    return ResponseEntity
        .ok(petMapper.toDto(pet));
  }

  @Override
  public ResponseEntity<Void> updatePetWithForm(Long petId, String name, PetStatusDto status) {
    var petDto = new PetDto()
        .id(petId)
        .name(name)
        .status(status);
    petService.updatePet(petDto);
    return ResponseEntity
        .noContent()
        .build();
  }

  @Override
  public ResponseEntity<ApiResponseDto> uploadFile(Long petId, String additionalMetadata, Resource body) {
    var filename = body.getFilename();
    if (filename == null) {
      var response = new ApiResponseDto()
          .code(500)
          .message("Error getting filename");
      return ResponseEntity
          .ok(response);
    }

    try {
      var directory = new File("images", petId.toString());
      if (!directory.mkdirs()) {
        log.error("Error creating directory: {}", directory.getAbsolutePath());
        var response = new ApiResponseDto()
            .code(500)
            .message("Error creating directory");
        return ResponseEntity
            .ok(response);
      }

      var file = new File(filename);
      log.info("Uploading to {}", file.getAbsolutePath());
      IOUtils.copy(body.getInputStream(), new FileOutputStream(file));

      var response = new ApiResponseDto()
          .code(200)
          .message("Success");
      return ResponseEntity
          .ok(response);
    } catch (IOException e) {
      log.error("Error uploading file: {}", filename, e);
      var response = new ApiResponseDto()
          .code(500)
          .type(e.getClass().getSimpleName())
          .message(e.getMessage());
      return ResponseEntity
          .ok(response);
    }
  }
}
