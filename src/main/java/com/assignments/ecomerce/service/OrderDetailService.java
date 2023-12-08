package com.assignments.ecomerce.service;

import com.assignments.ecomerce.model.OrderDetail;
import com.assignments.ecomerce.repository.OrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailService {
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    public List<OrderDetail> getAllOrderDetail() {
        return (List<OrderDetail>) orderDetailRepository.findAll();
    }

//    public List<OrderDetail> findAllByOrderId(Integer orderId) {
//        System.out.println(orderId);
//        return orderDetailRepository.findAllByOrderId(orderId);
//    }

    public List<OrderDetail> findListProductByOrderId(Integer orderId) {
        return orderDetailRepository.findListProductByOrderId(orderId);
    }

    public void save(OrderDetail orderDetail) {
        orderDetailRepository.save(orderDetail);
    }
}
