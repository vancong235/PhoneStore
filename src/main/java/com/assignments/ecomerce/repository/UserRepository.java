package com.assignments.ecomerce.repository;

import com.assignments.ecomerce.model.Category;
import com.assignments.ecomerce.model.Product;
import com.assignments.ecomerce.model.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<Users,Integer> {
    @Query("SELECT p from Users p where p.fullname = :fullname and status = 1")
    Users findByFullname(@Param("fullname") String fullname);

    @Query("SELECT p from Users p where p.email = :email and status = 1")
    Users findByEmail (@Param("email") String email);

    @Query("SELECT p FROM Users p WHERE  p.status = 1 AND CONCAT(p.fullname, p.email, p.role) LIKE %:keyword%")
    List<Users> searchByKeyword(@Param("keyword") String keyword);


    @Query("SELECT p from Users p where status = 1")
    Page<Users> pageUsers(Pageable pageable);
}
