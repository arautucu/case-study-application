package com.ing.hubs.case_study.repository;

import com.ing.hubs.case_study.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomerId(Long customerId);

    @Query("SELECT o FROM Order o WHERE o.status = 'PENDING' AND o.orderSide = :side")
    List<Order> findPendingOrdersBySide(Order.OrderSide side);
}
