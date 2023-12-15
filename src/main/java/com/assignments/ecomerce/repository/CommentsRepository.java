package com.assignments.ecomerce.repository;

import com.assignments.ecomerce.model.CartDetail;
import com.assignments.ecomerce.model.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentsRepository extends JpaRepository<Comments,Integer> {
    @Query(value = "SELECT * FROM comments WHERE productId = :productId", nativeQuery = true)
    List<Comments> findByProductId(@Param("productId") Integer productId);
}
