package com.ing.hubs.case_study.service;

import com.ing.hubs.case_study.entities.Order;

import java.util.List;

public interface OrderService {

    Order createOrder(Order order);
    List<Order> getOrdersByCustomerId(Long customerId);
    void cancelOrder(Long orderId);
    void matchOrders();
}
