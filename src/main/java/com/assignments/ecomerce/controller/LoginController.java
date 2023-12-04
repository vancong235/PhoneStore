package com.assignments.ecomerce.controller;

import com.assignments.ecomerce.dto.UserDto;
import com.assignments.ecomerce.model.Category;
import com.assignments.ecomerce.model.Product;
import com.assignments.ecomerce.model.Users;
import com.assignments.ecomerce.service.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
public class LoginController {

    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;

    @Autowired
    private RolePermissionsService rolePermissionsService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    CookieService cookieService;
    @Autowired
    UserDetailsService userDetailsService;


    @GetMapping("/register")
    public String register(Model model, UserDto userDto) {
        model.addAttribute("user", userDto);
        return "register";
    }

    @PostMapping("/register")
    public String registerSave(@ModelAttribute("user") UserDto userDto, Model model) {
        Users user = userService.findByEmail(userDto.getEmail());
        if (user != null) {
            System.out.println(user.getEmail());
            model.addAttribute("userexist", user);
            return "register";

        }
        userService.save(userDto);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("user-page")
    public String userPage(Model model, Principal principal, @RequestParam(value = "remember", required = false) boolean remember) {
        if (principal != null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());

            Users user = userService.findByEmail(principal.getName());
            model.addAttribute("userId", user.getId());
            model.addAttribute("user", userDetails);
            model.addAttribute("name", principal.getName());


            List<Category> categories = categoryService.getAllCategory();
            Page<Product> listProducts = productService.searchProducts(0, "", 9);

            model.addAttribute("categories", categories);
            model.addAttribute("size", listProducts.getSize());
            model.addAttribute("listProducts", listProducts);
            model.addAttribute("currentPage", 0);
            model.addAttribute("totalPages", listProducts.getTotalPages());

            return "index";
        } else {
            // User is not logged in, redirect to login page
            return "redirect:/login";
        }
    }

    @GetMapping("admin-page")
    public String adminPage (Model model, Principal principal, @RequestParam(value = "remember", required = false) boolean remember, HttpServletResponse response) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        Users user = userService.findByEmail(principal.getName());

        int countProduct = productService.countProducts();
        int countCustomer = customerService.countCustomers();
        int countOrder = orderService.countOrders();
        Double total = productService.getTotalRevenue();

        model.addAttribute("total", total);
        model.addAttribute("countOrder", countOrder);
        model.addAttribute("countProduct", countProduct);
        model.addAttribute("countCustomer", countCustomer);


        model.addAttribute("name", principal.getName());
        model.addAttribute("user", user);

        String username = userDetails.getUsername();
        String password = passwordEncoder.encode(userDetails.getPassword());


        System.out.println(userDetails.getPassword());

        return "statistical";
    }

    @GetMapping("manager-page")
    public String managerPage (Model model, Principal principal) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        Users user = userService.findByEmail(principal.getName());


        List<Category> categories = categoryService.getAllCategory();
        List<Product> listProducts = productService.getAllProducts();

        model.addAttribute("listProducts", listProducts);
        model.addAttribute("categories", categories);

        model.addAttribute("name", principal.getName());
        model.addAttribute("user", user);
        System.out.println(user.getRole());

        return "statistical";
    }
}
