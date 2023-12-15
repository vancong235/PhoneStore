package com.assignments.ecomerce.controller;

import com.assignments.ecomerce.model.*;
import com.assignments.ecomerce.service.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Date;

@Controller
public class CommentsController {


    @Autowired
    CommentsService commentsService;
    @Autowired
    ProductService productService;
    @Autowired
    OrderDetailService orderDetailService;
    @Autowired
    CustomerService customerService;
    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    UserService userService;

    @GetMapping("/comment")
    public String userPage(Model model, Principal principal) {

        return "comment";
    }

    @PostMapping("/sendComment")
    public String sendComment(Model model, Principal principal, HttpServletRequest request, RedirectAttributes redirectAttributes,
                              @RequestParam("content") String content, @RequestParam("star") Integer star, @RequestParam("productId") Integer productId) {
        Customer customer = customerService.findByEmail(principal.getName());
        Product product = productService.findById(productId);


        Comments comments = new Comments();
        comments.setContent(content);
        comments.setProduct(product);
        comments.setStar(star);
        comments.setCustomer(customer);
        Date currentTime = new Date();
        comments.setTime(currentTime);
        Integer idOrder = orderDetailService.findOrderIdBought(customer.getId(), productId);
        comments.setOrderId(idOrder);
        boolean flag = commentsService.saveComments(comments);
        if (flag) {
            System.out.println("Thành công");
        } else {
            System.out.println("Thất bại rồi");
        }

        // Truyền dữ liệu vào model (nếu cần)
        model.addAttribute("content", content);
        model.addAttribute("star", star);

        // Lấy URL trước đó từ request
        String referer = request.getHeader("Referer");

        // Chuyển hướng về trang trước đó
        redirectAttributes.addFlashAttribute("successMessage", "Thành công"); // Thông báo thành công (nếu cần)
        return "redirect:" + referer;
    }
}
