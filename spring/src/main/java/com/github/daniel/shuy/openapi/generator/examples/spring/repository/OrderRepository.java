package com.github.daniel.shuy.openapi.generator.examples.spring.repository;

import com.github.daniel.shuy.openapi.generator.examples.spring.model.Inventory;
import com.github.daniel.shuy.openapi.generator.examples.spring.model.Order;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends IdentifiableRepository<Long, Order> {
  @Query("SELECT status AS status, SUM(quantity) AS quantity FROM Order group by status")
  List<Inventory> getInventory();
}
