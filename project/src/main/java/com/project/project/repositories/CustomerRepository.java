package com.project.project.repositories;

import com.project.project.entities.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends CrudRepository<Customer,Integer> {

    Page<Customer> findAll(Pageable pageable);

    Optional<Customer> findByUsername(String username);

}
