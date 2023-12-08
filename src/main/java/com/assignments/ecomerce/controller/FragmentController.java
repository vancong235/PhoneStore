//package com.assignments.ecomerce.controller;
//
//import com.assignments.ecomerce.model.Orders;
//import com.assignments.ecomerce.model.Product;
//import com.assignments.ecomerce.model.Users;
//import com.assignments.ecomerce.service.*;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import java.security.Principal;
//import java.text.DecimalFormat;
//import java.time.LocalDate;
//import java.util.List;
//
//@Controller
//public class FragmentController {
//    DecimalFormat decimalFormat = new DecimalFormat("#.0");
//
//    @Autowired
//    private ProductService productService;
//
//    @Autowired
//    private OrderDetailService orderDetailService;
//
//    @Autowired
//    private EmployeeService employeeService;
//    @Autowired
//    private CategoryService categoryService;
//    @Autowired
//    private CustomerService customerService;
//    @Autowired
//    private OrderService orderService;
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private RolePermissionsService rolePermissionsService;
//
//    @Autowired
//    PasswordEncoder passwordEncoder;
//
//    @Autowired
//    CookieService cookieService;
//    @Autowired
//    UserDetailsService userDetailsService;
//    @GetMapping("statistical")
//    public String adminPage (Model model, Principal principal, @RequestParam(value = "remember", required = false) boolean remember, HttpServletResponse response) {
//        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
//        Users user = userService.findByEmail(principal.getName());
//
//
//        List<Product> recentlyAddedProducts = productService.get5RecentlyAddedProducts();
//        List<Orders> recentlyAddedOrders = orderService.get5RecentlyAddedOrders();
//        int countProduct = productService.countProducts();
//        int countCustomer = customerService.countCustomers();
//        int countOrder = orderService.countOrders();
//        int countEmployee = employeeService.countEmployee();
//        Double total = productService.getTotalRevenue();
//
//        int thisYear = LocalDate.now().getYear();
//        int thisMonth = LocalDate.now().getMonthValue();
//
//        //list total of month
//        List<Double> getMonthlyRevenue = orderService.getMonthlyRevenue(thisYear);
//        List<Double> getMonthlyRevenueLastYear = orderService.getMonthlyRevenue(thisYear-1);
//        //total of 12 month this year
//        Double totalRevenueOfThisYear = 0.0 ;
//        for(double month : getMonthlyRevenue){
//            totalRevenueOfThisYear += month;
//        }
//        // percen %
//        double percentageGrowth = 0.0;
//
//        if(thisMonth == 1){
//            Double percen = ((getMonthlyRevenue.get(thisMonth-1) - getMonthlyRevenueLastYear.get(12)) / getMonthlyRevenueLastYear.get(12)) * 100;
//            percentageGrowth = Double.parseDouble(decimalFormat.format(percen));
//        }
//        else {
//            Double percen = ((getMonthlyRevenue.get(thisMonth-1) - getMonthlyRevenue.get(thisMonth-2)) / getMonthlyRevenue.get(thisMonth-2)) * 100;
//            percentageGrowth = Double.parseDouble(decimalFormat.format(percen));
//        }
//
//        Double totalProfit = totalRevenueOfThisYear - 10000; //10.000 is example cost data
//
//
//        //get top 5 product sales
//        List<Product> top5product = orderDetailService.getTop5ProductSale(thisYear);
//
//
//        model.addAttribute("total", total);
//        model.addAttribute("totalRevenueOfThisYear", totalRevenueOfThisYear);
//        model.addAttribute("top5product", top5product);
//        model.addAttribute("totalProfit", totalProfit);
//        model.addAttribute("percentageGrowth", percentageGrowth);
//        model.addAttribute("salesData", getMonthlyRevenue);
//        model.addAttribute("salesDataLastYear", getMonthlyRevenueLastYear);
//        model.addAttribute("countOrder", countOrder);
//        model.addAttribute("countProduct", countProduct);
//        model.addAttribute("countCustomer", countCustomer);
//        model.addAttribute("recentlyAddedProducts", recentlyAddedProducts);
//        model.addAttribute("recentlyAddedOrders", recentlyAddedOrders);
//        model.addAttribute("name", principal.getName());
//        model.addAttribute("user", user);
//        model.addAttribute("countEmployee", countEmployee);
//
//
//        return "statistical";
//    }
//}
