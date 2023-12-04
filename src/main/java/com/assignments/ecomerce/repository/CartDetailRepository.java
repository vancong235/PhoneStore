package com.assignments.ecomerce.repository;

import com.assignments.ecomerce.model.CartDetail;
import com.assignments.ecomerce.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
<<<<<<< Updated upstream
public interface CartDetailRepository extends JpaRepository<CartDetail, Integer> {
    CartDetail findByUserIdAndProductId(Integer userId, Integer productId);

    List<CartDetail> findByUserId(Integer userId);
=======
public interface CartDetailRepository extends JpaRepository<CartDetail,Integer> {
>>>>>>> Stashed changes
}
