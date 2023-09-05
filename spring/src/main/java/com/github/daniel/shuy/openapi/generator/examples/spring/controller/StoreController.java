package com.github.daniel.shuy.openapi.generator.examples.spring.controller;

import com.github.daniel.shuy.openapi.generator.examples.spring.api.StoreApi;
import com.github.daniel.shuy.openapi.generator.examples.spring.dto.OrderDto;
import com.github.daniel.shuy.openapi.generator.examples.spring.mapper.OrderMapper;
import com.github.daniel.shuy.openapi.generator.examples.spring.service.OrderService;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StoreController implements StoreApi {
  private final OrderMapper orderMapper;
  private final OrderService orderService;

  @Override
  public ResponseEntity<Void> deleteOrder(Long orderId) {
    orderService.deleteOrder(orderId);
    return ResponseEntity
        .noContent()
        .build();
  }

  @Override
  public ResponseEntity<Map<String, Integer>> getInventory() {
    var inventory = orderService.getInventory()
        .entrySet()
        .stream()
        .collect(Collectors.toMap(entry -> entry.getKey().toString(), Map.Entry::getValue));
    return ResponseEntity
        .ok(inventory);
  }

  @Override
  public ResponseEntity<OrderDto> getOrderById(Long orderId) {
    var order = orderService.getOrderById(orderId);
    return ResponseEntity
        .ok(orderMapper.toDto(order));
  }

  @Override
  public ResponseEntity<OrderDto> placeOrder(OrderDto orderDto) {
    var order = orderService.addOrder(orderDto);
    return ResponseEntity
        .ok(orderMapper.toDto(order));
  }
}
