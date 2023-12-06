package com.assignments.ecomerce.controller;

import com.assignments.ecomerce.model.*;

import com.assignments.ecomerce.service.CartDetailService;
import com.assignments.ecomerce.service.OrderService;
import com.assignments.ecomerce.service.ProductService;
import com.assignments.ecomerce.service.UserService;

import com.assignments.ecomerce.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

import java.text.DecimalFormat;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

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

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private CouponService couponService;


    @GetMapping("/sale")
    public String getAllOrder1() {
        return "sale";
    }
    @GetMapping("/order/{pageNo}")
    public String getAllOrder(@PathVariable("pageNo") int pageNo, Model model, Principal principal) {
        Page<Orders> listOrder = orderService.pageOrders(pageNo);
        Users user = userService.findByEmail(principal.getName());
        Employee employee = employeeService.findByEmail(principal.getName());
        //System.out.println(employee.getName());
        model.addAttribute("employee", employee);
        model.addAttribute("user", user);
        model.addAttribute("listOrder", listOrder);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", listOrder.getTotalPages());
        return "order";
    }


    @GetMapping("/update-orders")
    public String update(Orders orders, RedirectAttributes attributes) {
        try {
            orderService.update(orders);
            attributes.addFlashAttribute("success", "Order confirm successfully !");
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            attributes.addFlashAttribute("error", "Failed to confirm, something was wrong !");
        } catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute("error", "Error server");
        }
        return "redirect:/order/0";
    }
    @GetMapping("/search-order/{pageNo}")
    public String searchOrder(@PathVariable("pageNo") int pageNo,
                                 @RequestParam("keyword") String keyword,
                                 Model model, Principal principal) {
        Page<Orders> listOrder = orderService.searchOrders(pageNo, keyword);
        Users user = userService.findByEmail(principal.getName());

        model.addAttribute("user", user);
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

    @RequestMapping(value = "/cancelOrder", method = {RequestMethod.PUT, RequestMethod.GET})
    public String deletedProduct(Integer id, RedirectAttributes redirectAttributes, Principal principal) {
        try {

            orderService.cancelOrder(id);
            redirectAttributes.addFlashAttribute("success", "Cancel Order successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Cancel failed!");
        }
        return "redirect:/order/0";
    }

    @GetMapping("/detail-order/{id}")
    public String DetailOrder(@PathVariable("id") Integer id, Model model) {
        Orders order = orderService.findById(id);
        Coupon coupon = couponService.findById(order.getCouponId());

        //convert date to localDate
        LocalDate localOrderDate = order.getOrderDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate startDeliveryDate = localOrderDate.plusDays(3);
        LocalDate endDeliveryDate = localOrderDate.plusDays(7);

        List<OrderDetail> orderDetailList = orderDetailService.findListProductByOrderId(id);

        model.addAttribute("orderDetailList", orderDetailList);
        model.addAttribute("order", order);
        model.addAttribute("coupon", coupon);
        model.addAttribute("endDeliveryDate", endDeliveryDate);
        model.addAttribute("startDeliveryDate", startDeliveryDate);

        return "detailOrder";
    }

}
