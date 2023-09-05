package com.github.daniel.shuy.openapi.generator.examples.spring.service;

import com.github.daniel.shuy.openapi.generator.examples.spring.dto.OrderDto;
import com.github.daniel.shuy.openapi.generator.examples.spring.mapper.OrderMapper;
import com.github.daniel.shuy.openapi.generator.examples.spring.model.Inventory;
import com.github.daniel.shuy.openapi.generator.examples.spring.model.Order;
import com.github.daniel.shuy.openapi.generator.examples.spring.model.Pet;
import com.github.daniel.shuy.openapi.generator.examples.spring.repository.OrderRepository;
import com.github.daniel.shuy.openapi.generator.examples.spring.util.exception.BadRequestException;
import com.github.daniel.shuy.openapi.generator.examples.spring.util.exception.NotFoundException;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
  private final OrderMapper orderMapper;
  private final OrderRepository orderRepository;
  private final PetService petService;

  public Order addOrder(OrderDto orderDto) {
    var pet = getPet(orderDto);
    var order = orderMapper.fromDto(orderDto);
    order.setPet(pet);
    return orderRepository.save(order);
  }

  public Order getOrderById(Long orderId) {
    return orderRepository.findById(orderId)
        .orElseThrow(() -> new NotFoundException("Order not found"));
  }

  public Map<OrderDto.StatusEnum, Integer> getInventory() {
    return orderRepository.getInventory()
        .stream()
        .collect(Collectors.toMap(Inventory::getStatus, Inventory::getQuantity));
  }

  public void deleteOrder(Long orderId) {
    var order = getOrderById(orderId);

    orderRepository.delete(order);
  }

  protected Pet getPet(OrderDto orderDto) {
    var petId = orderDto.getPetId();
    if (petId == null) {
      return null;
    }

    return petService.getPetById(orderDto.getPetId());
  }
}
