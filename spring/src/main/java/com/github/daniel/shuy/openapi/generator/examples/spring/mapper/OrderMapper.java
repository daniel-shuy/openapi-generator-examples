package com.github.daniel.shuy.openapi.generator.examples.spring.mapper;

import com.github.daniel.shuy.openapi.generator.examples.spring.dto.OrderDto;
import com.github.daniel.shuy.openapi.generator.examples.spring.model.Order;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = SpringMapperConfig.class,
    uses = {
        PetMapper.class,
    })
public interface OrderMapper {
  @BeanMapping(ignoreUnmappedSourceProperties = {
      "petId",
  })
  @Mapping(target = "pet", ignore = true)
  Order fromDto(OrderDto orderDto);

  @Mapping(target = "petId", source = "pet.id")
  OrderDto toDto(Order order);
}
