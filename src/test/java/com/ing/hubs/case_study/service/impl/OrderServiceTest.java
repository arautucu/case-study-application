package com.ing.hubs.case_study.service.impl;

import com.ing.hubs.case_study.entities.Customer;
import com.ing.hubs.case_study.entities.Order;
import com.ing.hubs.case_study.repository.OrderRepository;
import com.ing.hubs.case_study.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private OrderServiceImpl orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateOrder() {
        Customer customer = new Customer(1L, "John Doe", "password", 500.0);
        Order order = Order.builder()
                .customerId(1L)
                .assetName("Stock A")
                .orderSide(Order.OrderSide.BUY)
                .size(10)
                .price(20.0)
                .status(Order.OrderStatus.PENDING)
                .build();

        when(customerService.getCustomerById(1L)).thenReturn(customer);
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Order createdOrder = orderService.createOrder(order);
        assertNotNull(createdOrder);
        assertEquals(Order.OrderStatus.PENDING, createdOrder.getStatus());
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void testCreateOrder_InsufficientBalance() {
        Customer customer = new Customer(1L, "John Doe", "password", 100.0);
        Order order = Order.builder()
                .customerId(1L)
                .assetName("Stock A")
                .orderSide(Order.OrderSide.BUY)
                .size(10)
                .price(20.0)
                .build();

        when(customerService.getCustomerById(1L)).thenReturn(customer);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            orderService.createOrder(order);
        });

        assertEquals("Insufficient balance to create order.", exception.getMessage());
        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    void testGetOrdersByCustomerId() {
        Order order1 = new Order(1L, 1L, "Stock A", Order.OrderSide.BUY, 10, 20.0, Order.OrderStatus.PENDING, null);
        Order order2 = new Order(2L, 1L, "Stock B", Order.OrderSide.SELL, 5, 50.0, Order.OrderStatus.PENDING, null);

        when(orderRepository.findByCustomerId(1L)).thenReturn(Arrays.asList(order1, order2));

        List<Order> orders = orderService.getOrdersByCustomerId(1L);
        assertEquals(2, orders.size());
        verify(orderRepository, times(1)).findByCustomerId(1L);
    }

    @Test
    void testCancelOrder() {
        Order order = Order.builder()
                .id(1L)
                .customerId(1L)
                .orderSide(Order.OrderSide.BUY)
                .size(10)
                .price(20.0)
                .status(Order.OrderStatus.PENDING)
                .build();

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        orderService.cancelOrder(1L);
        assertEquals(Order.OrderStatus.CANCELED, order.getStatus());
        verify(orderRepository, times(1)).save(order);
    }
}
