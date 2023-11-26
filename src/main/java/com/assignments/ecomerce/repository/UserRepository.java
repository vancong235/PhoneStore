package com.assignments.ecomerce.repository;

import com.assignments.ecomerce.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users,Integer> {
    Users findByFullname(String fullname);
    Users findByEmail (String email);


}
