package com.ing.hubs.case_study.controller;

import org.springframework.web.bind.annotation.*;
import com.ing.hubs.case_study.entities.Customer;
import com.ing.hubs.case_study.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/create")
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerService.createCustomer(customer);
    }

    @PostMapping("/{customerId}/deposit")
    public Customer depositMoney(@PathVariable Long customerId, @RequestParam double amount) {
        return customerService.depositMoney(customerId, amount);
    }

    @PostMapping("/{customerId}/withdraw")
    public Customer withdrawMoney(@PathVariable Long customerId, @RequestParam double amount) {
        return customerService.withdrawMoney(customerId, amount);
    }

    @GetMapping("/{customerId}")
    public Customer getCustomerById(@PathVariable Long customerId) {
        return customerService.getCustomerById(customerId);
    }
}
