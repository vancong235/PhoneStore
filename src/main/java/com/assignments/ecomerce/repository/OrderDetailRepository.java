package com.assignments.ecomerce.repository;

import com.assignments.ecomerce.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail,Integer> {
    List<OrderDetail> findAllByOrderId(Integer orderId);
}
