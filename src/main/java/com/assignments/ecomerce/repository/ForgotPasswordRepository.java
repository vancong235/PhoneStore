package com.assignments.ecomerce.repository;

import com.assignments.ecomerce.model.ForgotPasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ForgotPasswordRepository extends JpaRepository<ForgotPasswordToken,Integer> {

    ForgotPasswordToken findByToken(String token);
}
