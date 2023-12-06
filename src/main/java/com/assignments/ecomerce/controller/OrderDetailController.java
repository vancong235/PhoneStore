package com.assignments.ecomerce.controller;

import com.assignments.ecomerce.model.OrderDetail;
import com.assignments.ecomerce.model.Orders;
import com.assignments.ecomerce.service.OrderDetailService;
import com.assignments.ecomerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class OrderDetailController {
    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private OrderService orderService;
    @GetMapping("/orderdetail")
    public String getAllOrderDetail(@RequestParam("orderId") Integer orderId,Model model) {
        //List<OrderDetail> listOrderDetail = orderDetailService.getAllOrderDetail();
        List<Orders> listOrder = new ArrayList<>();
        Orders order = orderService.getOrderById(orderId);
        if (order != null) {
            listOrder.add(order);
        }
        model.addAttribute("listOrder", listOrder);

        List<OrderDetail> listOrderDetail = orderDetailService.findListProductByOrderId(orderId);
        model.addAttribute("listOrderDetail", listOrderDetail);
        return "orderdetail";
    }
}
