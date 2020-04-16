package com.project.project.repositories;

import com.project.project.entities.Address;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends CrudRepository<Address, Integer> {

    @Query(value = "select * from address where user_id=:id and label=:label", nativeQuery = true)
    Optional<Address> findByAdd(@Param("label") String label, @Param("id") Integer id);

    @Modifying
    @Query(value = "delete from address where user_id=:userId and id=:AddressId", nativeQuery = true)
    void deleteAdd(@Param("userId") Integer userId, @Param("AddressId") Integer AddressId);

}
