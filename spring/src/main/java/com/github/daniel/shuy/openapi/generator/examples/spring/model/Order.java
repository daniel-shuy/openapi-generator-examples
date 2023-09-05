package com.github.daniel.shuy.openapi.generator.examples.spring.model;

import com.github.daniel.shuy.openapi.generator.examples.spring.dto.OrderDto;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.Instant;
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
public class Order extends Identifiable<Long> {
  @Id
  @GeneratedValue
  private Long id;

  @ManyToOne
  private Pet pet;

  private Integer quantity;

  private Instant shipDate;

  @Enumerated(EnumType.STRING)
  private OrderDto.StatusEnum status;

  private Boolean complete;
}
