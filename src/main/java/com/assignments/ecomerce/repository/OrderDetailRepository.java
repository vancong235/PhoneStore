package com.assignments.ecomerce.repository;

import com.assignments.ecomerce.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail,Integer>{
//    List<OrderDetail> findAllByOrderId(Integer orderId);

    @Query(value = "SELECT * FROM OrderDetail ", nativeQuery = true)
    List<OrderDetail> findAll();
    @Query(value = "SELECT * FROM OrderDetail WHERE orderId = :orderId", nativeQuery = true)
    List<OrderDetail> findListProductByOrderId(@Param("orderId") Integer orderId);
}
