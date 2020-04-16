package com.project.project.repositories;

import com.project.project.entities.Customer;
import com.project.project.entities.Seller;
import com.project.project.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    User findByUsername(String username);

    User findByEmailIgnoreCase(String email);

}
