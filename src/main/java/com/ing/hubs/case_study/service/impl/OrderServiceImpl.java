package com.ing.hubs.case_study.service.impl;

import com.ing.hubs.case_study.repository.OrderRepository;
import com.ing.hubs.case_study.service.CustomerService;
import com.ing.hubs.case_study.service.OrderService;
import com.ing.hubs.case_study.entities.Order;
import com.ing.hubs.case_study.entities.Customer;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerService customerService;

    @Override
    public Order createOrder(Order order) {
        if (order.getOrderSide() == Order.OrderSide.BUY) {
            Customer customer = customerService.getCustomerById(order.getCustomerId());
            double totalPrice = order.getSize() * order.getPrice();
            if (customer.getBalance() < totalPrice) {
                throw new IllegalArgumentException("Insufficient balance to create order.");
            }
            customerService.withdrawMoney(order.getCustomerId(), totalPrice);
        }
        order.setStatus(Order.OrderStatus.PENDING);
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getOrdersByCustomerId(Long customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    @Override
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found."));

        if (order.getStatus() != Order.OrderStatus.PENDING) {
            throw new IllegalArgumentException("Only pending orders can be canceled.");
        }

        if (order.getOrderSide() == Order.OrderSide.BUY) {
            double refundAmount = order.getSize() * order.getPrice();
            customerService.depositMoney(order.getCustomerId(), refundAmount);
        }

        order.setStatus(Order.OrderStatus.CANCELED);
        orderRepository.save(order);
    }

    @Transactional
    public void matchOrders() {
        List<Order> buyOrders = orderRepository.findPendingOrdersBySide(Order.OrderSide.BUY);
        List<Order> sellOrders = orderRepository.findPendingOrdersBySide(Order.OrderSide.SELL);

        for (Order buyOrder : buyOrders) {
            for (Order sellOrder : sellOrders) {
                if (buyOrder.getAssetName().equals(sellOrder.getAssetName()) &&
                        buyOrder.getSize() == sellOrder.getSize()) {

                    sellOrder.setStatus(Order.OrderStatus.MATCHED);

                    updateCustomerAssets(buyOrder, sellOrder);

                    orderRepository.save(buyOrder);
                    orderRepository.save(sellOrder);

                    break;
                }
            }
        }
    }

    private void updateCustomerAssets(Order buyOrder, Order sellOrder) {
        Customer buyer = customerService.getCustomerById(buyOrder.getCustomerId());
        Customer seller = customerService.getCustomerById(sellOrder.getCustomerId());

        double cost = buyOrder.getSize() * buyOrder.getPrice();
        buyer.setBalance(buyer.getBalance() - cost);

        seller.setBalance(seller.getBalance() + cost);

        customerService.updateCustomer(buyer);
        customerService.updateCustomer(seller);
    }
}
