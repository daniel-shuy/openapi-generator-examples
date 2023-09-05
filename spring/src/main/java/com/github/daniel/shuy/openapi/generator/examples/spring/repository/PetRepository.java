package com.github.daniel.shuy.openapi.generator.examples.spring.repository;

import com.github.daniel.shuy.openapi.generator.examples.spring.dto.PetStatusDto;
import com.github.daniel.shuy.openapi.generator.examples.spring.model.Pet;
import com.github.daniel.shuy.openapi.generator.examples.spring.model.Tag;
import java.util.Collection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PetRepository extends IdentifiableRepository<Long, Pet> {
  Page<Pet> findByStatusIn(Collection<PetStatusDto> petStatus, Pageable pageable);

  Page<Pet> findByTagsIn(Collection<Tag> tags, Pageable pageable);
}
