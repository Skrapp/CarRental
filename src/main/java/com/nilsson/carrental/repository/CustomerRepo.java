package com.nilsson.carrental.repository;

import com.nilsson.carrental.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long> {
    public Customer findByUsername(String username);
}
