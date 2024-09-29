package com.ing.hubs.case_study.controller;

import com.ing.hubs.case_study.entities.Order;
import com.ing.hubs.case_study.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public Order createOrder(@RequestBody Order order) {
        return orderService.createOrder(order);
    }

    @GetMapping("/{customerId}")
    public List<Order> getOrdersByCustomerId(@PathVariable Long customerId) {
        return orderService.getOrdersByCustomerId(customerId);
    }

    @DeleteMapping("/{orderId}")
    public void cancelOrder(@PathVariable Long orderId) {
        orderService.cancelOrder(orderId);
    }

    @PostMapping("/match")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> matchOrders() {
        try {
            orderService.matchOrders();
            return ResponseEntity.ok("Order matching completed successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Order matching failed.");
        }
    }
}
