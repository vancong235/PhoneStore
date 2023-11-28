package com.assignments.ecomerce.controller;

import com.assignments.ecomerce.model.Orders;
import com.assignments.ecomerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/sale")
    public String getAllOrder1() {
        return "sale";
    }
    @GetMapping("/order/{pageNo}")
    public String getAllOrder(@PathVariable("pageNo") int pageNo, Model model, Principal principal) {
        Page<Orders> listOrder = orderService.pageOrders(pageNo);
        model.addAttribute("listOrder", listOrder);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", listOrder.getTotalPages());
        return "order";
    }

    @GetMapping("/search-order/{pageNo}")
    public String searchOrder(@PathVariable("pageNo") int pageNo,
                                 @RequestParam("keyword") String keyword,
                                 Model model, Principal principal) {
        Page<Orders> listOrder = orderService.searchOrders(pageNo, keyword);
        model.addAttribute("size", listOrder.getSize());
        model.addAttribute("listOrder", listOrder);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", listOrder.getTotalPages());
        return "order";
    }

}
