package com.ing.hubs.case_study.repository;

import com.ing.hubs.case_study.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
