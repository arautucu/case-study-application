package com.ing.hubs.case_study.service.impl;

import com.ing.hubs.case_study.entities.Customer;
import com.ing.hubs.case_study.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCustomer() {
        Customer customer = Customer.builder()
                .name("John Doe")
                .password("password")
                .balance(0.0)
                .build();

        when(customerRepository.save(customer)).thenReturn(customer);

        Customer savedCustomer = customerService.createCustomer(customer);
        assertNotNull(savedCustomer);
        assertEquals("John Doe", savedCustomer.getName());
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    void testDepositMoney() {
        Customer customer = Customer.builder()
                .id(1L)
                .name("John Doe")
                .balance(100.0)
                .build();

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        Customer updatedCustomer = customerService.depositMoney(1L, 50.0);
        assertEquals(150.0, updatedCustomer.getBalance());
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    void testWithdrawMoney() {
        Customer customer = Customer.builder()
                .id(1L)
                .name("John Doe")
                .balance(100.0)
                .build();

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        Customer updatedCustomer = customerService.withdrawMoney(1L, 50.0);
        assertEquals(50.0, updatedCustomer.getBalance());
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    void testWithdrawMoney_InsufficientFunds() {
        Customer customer = Customer.builder()
                .id(1L)
                .name("John Doe")
                .balance(30.0)
                .build();

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            customerService.withdrawMoney(1L, 50.0);
        });

        assertEquals("Insufficient balance.", exception.getMessage());
        verify(customerRepository, never()).save(any(Customer.class));
    }

    @Test
    void testGetCustomerById_NotFound() {
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            customerService.getCustomerById(1L);
        });

        assertEquals("Customer not found.", exception.getMessage());
    }
}
