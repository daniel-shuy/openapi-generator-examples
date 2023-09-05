package com.github.daniel.shuy.openapi.generator.examples.spring.model;

import jakarta.persistence.MappedSuperclass;
import java.io.Serializable;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@MappedSuperclass
@NoArgsConstructor
@SuperBuilder
public abstract class Identifiable<T extends Serializable> {
  public abstract T getId();

  public abstract void setId(T id);

  // Only include @Id in equals()/hashCode()/toString() to avoid LazyInitializationException
  @Override
  public final boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (!(obj instanceof Identifiable<?> that)) {
      return false;
    }

    var id = getId();

    // prevent Transient entities from being considered equal
    if (id == null) {
      return false;
    }

    return id == that.getId();
  }

  @Override
  public final int hashCode() {
    var id = getId();

    // prevent Transient entities from generating the same hashCode
    return id != null ? id.hashCode() : super.hashCode();
  }

  @Override
  public String toString() {
    return getClass().getSimpleName()
        + "(id=" + getId()
        + ')';
  }
}
