package com.github.daniel.shuy.openapi.generator.examples.spring.mapper;

import jakarta.annotation.Nullable;
import org.mapstruct.Condition;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.stereotype.Component;

@Component
public class JsonNullableMapper {
  @Nullable
  protected <T> T unwrap(JsonNullable<T> jsonNullable) {
    return jsonNullable.orElse(null);
  }

  protected <T> JsonNullable<T> wrap(T value) {
    return JsonNullable.of(value);
  }

  @Condition
  protected <T> boolean isNotEmpty(JsonNullable<T> field) {
    return field != null && field.isPresent();
  }
}
