package com.assignments.ecomerce.repository;

import com.assignments.ecomerce.model.Cart;
import com.assignments.ecomerce.model.Product;
import com.assignments.ecomerce.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart,Integer> {

}
