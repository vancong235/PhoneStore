package com.assignments.ecomerce.controller;

import com.assignments.ecomerce.model.*;
import com.assignments.ecomerce.service.CartDetailService;
import com.assignments.ecomerce.service.OrderService;
import com.assignments.ecomerce.service.ProductService;
import com.assignments.ecomerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.text.DecimalFormat;
import java.util.List;

@Controller
public class OrderController {
    @Autowired
    private CartDetailService cartDetailService;
    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;
    @Autowired
    UserDetailsService userDetailsService;

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
    @GetMapping("/checkout")
    public String orderProduct(Model model, Principal principal) {
        if (principal != null) {

            Users user = userService.findByEmail(principal.getName());
            List<CartDetail> list = cartDetailService.findByUserId(user.getId());
            Double sum = 0.0d;
            for (CartDetail object: list) {
                object.setProduct(productService.getProductById(object.getProductId()));
                sum+=(object.getProduct().getPrice()-(object.getProduct().getPrice()*object.getProduct().getDiscount()/100))*object.getQuantity();
            }
            DecimalFormat df = new DecimalFormat(",###,###");
            String promo = "UNDEFINED";
            Double promoPrice = 0d;

            model.addAttribute("promo", promo);
            model.addAttribute("promoPrice", df.format(Math.floor(promoPrice)));
            model.addAttribute("totalPrice", df.format(Math.floor(sum)));
            model.addAttribute("listCartDetail", list);
            model.addAttribute("userId", user.getId());
            model.addAttribute("name", principal.getName());

            return "checkout";
        } else {
            // User is not logged in, redirect to login page
            return "redirect:/login";
        }
    }
}
