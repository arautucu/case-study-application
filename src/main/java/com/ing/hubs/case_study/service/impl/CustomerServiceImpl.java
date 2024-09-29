package com.ing.hubs.case_study.service.impl;

import com.ing.hubs.case_study.repository.CustomerRepository;
import com.ing.hubs.case_study.service.CustomerService;
import com.ing.hubs.case_study.entities.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer depositMoney(Long customerId, double amount) {
        Customer customer = getCustomerById(customerId);
        customer.setBalance(customer.getBalance() + amount);
        return customerRepository.save(customer);
    }

    @Override
    public Customer withdrawMoney(Long customerId, double amount) {
        Customer customer = getCustomerById(customerId);
        if (customer.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient balance.");
        }
        customer.setBalance(customer.getBalance() - amount);
        return customerRepository.save(customer);
    }

    @Override
    public Customer getCustomerById(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found."));
    }

    @Override
    public Customer updateCustomer(Customer updatedCustomer) {
        Customer existingCustomer = customerRepository.findById(updatedCustomer.getId())
                .orElseThrow(() -> new IllegalArgumentException("Customer not found with ID: " + updatedCustomer.getId()));

        existingCustomer.setBalance(updatedCustomer.getBalance());

        return customerRepository.save(existingCustomer);
    }
}
