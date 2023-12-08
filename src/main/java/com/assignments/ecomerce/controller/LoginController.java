package com.assignments.ecomerce.controller;

import com.assignments.ecomerce.dto.CustomerDTO;
import com.assignments.ecomerce.dto.UserDto;

import com.assignments.ecomerce.model.Category;
import com.assignments.ecomerce.model.Customer;
import com.assignments.ecomerce.model.Product;
import com.assignments.ecomerce.model.Users;

import com.assignments.ecomerce.model.*;

import com.assignments.ecomerce.service.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.parameters.P;
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
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class LoginController {
    DecimalFormat decimalFormat = new DecimalFormat("#.0");

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private EmployeeService employeeService;
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
    public String registerSave(@ModelAttribute("user") UserDto userDto,
                               @RequestParam("phoneNumber") String phoneNumber,
                                @RequestParam("address") String address, Model model) {
        try {
            Users user = userService.findByEmail(userDto.getEmail());

            if (user != null) {
                System.out.println(user.getEmail());
                model.addAttribute("userexist", user);
                return "register";

            }
            userService.save(userDto);  // add user account to DB
            //add user info to DB
            CustomerDTO customer = new CustomerDTO();
            customer.setEmail(userDto.getEmail());
            customer.setName(userDto.getFullname());
            customer.setAddress(address);
            customer.setPhoneNumber(phoneNumber);
            customerService.save(customer);
            return "redirect:/login";
        }catch (Exception e){
            model.addAttribute("error", "Error server, something was wrong !");
        }
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
            model.addAttribute("email", principal.getName());
            model.addAttribute("name", user.getFullname());
            Customer customer = customerService.findByEmail(principal.getName());
            if (customer != null) {
                if (customer.getAddress() != null) {
                    model.addAttribute("address", customer.getAddress());
                } else {
                    model.addAttribute("address", "");
                }
            } else {
                model.addAttribute("address", "");
            }


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


        List<Product> recentlyAddedProducts = productService.get5RecentlyAddedProducts();
        List<Orders> recentlyAddedOrders = orderService.get5RecentlyAddedOrders();
        int countProduct = productService.countProducts();
        int countCustomer = customerService.countCustomers();
        int countOrder = orderService.countOrders();
        int countEmployee = employeeService.countEmployee();
        Double total = productService.getTotalRevenue();

        int thisYear = LocalDate.now().getYear();
        int thisMonth = LocalDate.now().getMonthValue();

        //list total of month
        List<Double> getMonthlyRevenue = orderService.getMonthlyRevenue(thisYear);
        List<Double> getMonthlyRevenueLastYear = orderService.getMonthlyRevenue(thisYear-1);
        //total of 12 month this year
        Double totalRevenueOfThisYear = 0.0 ;
        for(double month : getMonthlyRevenue){
            totalRevenueOfThisYear += month;
        }
        // percen %
        double percentageGrowth = 0.0;

        if(thisMonth == 1){
            Double percen = ((getMonthlyRevenue.get(thisMonth-1) - getMonthlyRevenueLastYear.get(12)) / getMonthlyRevenueLastYear.get(12)) * 100;
            percentageGrowth = Double.parseDouble(decimalFormat.format(percen));
        }
        else {
            Double percen = ((getMonthlyRevenue.get(thisMonth-1) - getMonthlyRevenue.get(thisMonth-2)) / getMonthlyRevenue.get(thisMonth-2)) * 100;
            percentageGrowth = Double.parseDouble(decimalFormat.format(percen));
        }

       Double totalProfit = totalRevenueOfThisYear - 10000; //10.000 is example cost data


        //get top 5 product sales
        List<Product> top5product = orderDetailService.getTop5ProductSale(thisYear);


        model.addAttribute("total", total);
        model.addAttribute("totalRevenueOfThisYear", totalRevenueOfThisYear);
        model.addAttribute("top5product", top5product);
        model.addAttribute("totalProfit", totalProfit);
        model.addAttribute("percentageGrowth", percentageGrowth);
        model.addAttribute("salesData", getMonthlyRevenue);
        model.addAttribute("salesDataLastYear", getMonthlyRevenueLastYear);
        model.addAttribute("countOrder", countOrder);
        model.addAttribute("countProduct", countProduct);
        model.addAttribute("countCustomer", countCustomer);
        model.addAttribute("recentlyAddedProducts", recentlyAddedProducts);
        model.addAttribute("recentlyAddedOrders", recentlyAddedOrders);
        model.addAttribute("name", principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("countEmployee", countEmployee);

        System.out.println(user.getRole());
        return "statistical";
    }

    @GetMapping("manager-page")
    public String managerPage (Model model, Principal principal) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        Users user = userService.findByEmail(principal.getName());
        Employee employee = employeeService.findByEmail(principal.getName());

        model.addAttribute("employee", employee);

        List<Category> categories = categoryService.getAllCategory();
        List<Product> listProducts = productService.getAllProducts();

        model.addAttribute("listProducts", listProducts);
        model.addAttribute("categories", categories);

        model.addAttribute("name", principal.getName());
        model.addAttribute("user", user);
        System.out.println(user.getRole());

        return "redirect:/product/0";
    }
}
