package com.ing.hubs.case_study.service;

import com.ing.hubs.case_study.entities.Customer;

public interface CustomerService {

    Customer createCustomer(Customer customer);
    Customer depositMoney(Long customerId, double amount);
    Customer withdrawMoney(Long customerId, double amount);
    Customer getCustomerById(Long customerId);
    Customer updateCustomer(Customer customer);
}
