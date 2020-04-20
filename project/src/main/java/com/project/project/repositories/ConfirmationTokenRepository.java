package com.project.project.repositories;

import com.project.project.tokens.ConfirmationToken;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfirmationTokenRepository extends CrudRepository<ConfirmationToken, Long> {

    ConfirmationToken findByConfirmationToken(String confirmationToken);

    @Modifying
    @Query(value = "delete from confirmation_token where confirmation_token=:token", nativeQuery = true)
    void delConfirmationToken(@Param("token") String token);

    ConfirmationToken findByUser(Integer uid);
}