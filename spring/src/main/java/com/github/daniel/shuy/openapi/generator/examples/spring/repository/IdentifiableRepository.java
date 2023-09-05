package com.github.daniel.shuy.openapi.generator.examples.spring.repository;

import com.github.daniel.shuy.openapi.generator.examples.spring.model.Identifiable;
import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IdentifiableRepository<ID extends Serializable, T extends Identifiable<ID>> extends JpaRepository<T, ID> {
}
