package com.github.daniel.shuy.openapi.generator.examples.spring.model;

import com.github.daniel.shuy.openapi.generator.examples.spring.dto.OrderDto;

public interface Inventory {
  OrderDto.StatusEnum getStatus();

  int getQuantity();
}
