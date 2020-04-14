package com.project.project.repositories;

import com.project.project.tokens.ResetPasswordToken;
import com.project.project.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface ResetPasswordRepository extends CrudRepository<ResetPasswordToken,Integer> {

    ResetPasswordToken findByUser(User user);

    ResetPasswordToken findByToken(String resetToken);
}
