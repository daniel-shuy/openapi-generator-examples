package com.github.daniel.shuy.openapi.generator.examples.spring.model;

import com.github.daniel.shuy.openapi.generator.examples.spring.dto.PetStatusDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import java.util.Collection;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Pet extends Identifiable<Long> {
  @Id
  @GeneratedValue
  private Long id;

  private String name;

  @ManyToOne
  private Category category;

  @ElementCollection(fetch = FetchType.EAGER)
  private Collection<String> photoUrls;

  @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private Set<Tag> tags;

  @Enumerated(EnumType.STRING)
  private PetStatusDto status;
}
