package com.assignments.ecomerce.repository;

import com.assignments.ecomerce.model.CartDetail;
import com.assignments.ecomerce.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface CartDetailRepository extends JpaRepository<CartDetail, Integer> {
    CartDetail findByUserIdAndProductId(Integer userId, Integer productId);

    List<CartDetail> findByUserId(Integer userId);

    @Query("SELECT cd FROM CartDetail cd JOIN FETCH cd.product p WHERE cd.userId = :userId")
    List<CartDetail> findHaveManyField(Integer userId);

    @Modifying
    @Query(value = "INSERT INTO cartdetail (userId, productId, quantity, unitPrice) VALUES (:userId, :productId, :quantity, :unitPrice)", nativeQuery = true)
    int saveCartDetail(@Param("userId") Integer userId, @Param("productId") Integer productId, @Param("quantity") Integer quantity, @Param("unitPrice") Double unitPrice);

}
