package com.github.daniel.shuy.openapi.generator.examples.spring.repository;

import com.github.daniel.shuy.openapi.generator.examples.spring.model.Tag;
import java.util.Collection;
import java.util.Set;

public interface TagRepository extends IdentifiableRepository<Long, Tag> {
  Set<Tag> findByIdIn(Collection<Long> ids);

  Set<Tag> findByName(String name);
}
