package com.assignments.ecomerce.repository;

import com.assignments.ecomerce.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users,Integer> {
    @Query("SELECT p from Users p where p.fullname = :fullname and status = 1")
    Users findByFullname(@Param("fullname") String fullname);

    @Query("SELECT p from Users p where p.email = :email and status = 1")
    Users findByEmail (@Param("email") String email);


}
