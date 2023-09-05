package com.github.daniel.shuy.openapi.generator.examples.spring;

import org.openapitools.jackson.nullable.JsonNullable;
import org.springdoc.core.utils.SpringDocUtils;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
  static {
    SpringDocUtils.getConfig().addResponseWrapperToIgnore(JsonNullable.class);
  }
}
