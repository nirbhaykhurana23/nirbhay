package com.project.project.repositories;

import com.project.project.tokens.ResetPasswordToken;
import com.project.project.entities.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ResetPasswordRepository extends CrudRepository<ResetPasswordToken,Integer> {

    ResetPasswordToken findByUser(User user);

    ResetPasswordToken findByToken(String resetToken);

    @Modifying
    @Query(value = "delete from reset_password_token where token=:token", nativeQuery = true)
    void delToken(@Param("token") String token);

}
