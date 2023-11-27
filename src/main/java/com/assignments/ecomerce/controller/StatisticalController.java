package com.assignments.ecomerce.controller;

import com.assignments.ecomerce.model.*;
import com.assignments.ecomerce.service.CustomerService;
import com.assignments.ecomerce.service.OrderService;
import com.assignments.ecomerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class StatisticalController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private OrderService orderService;

    @GetMapping("/statistical")
    public String countProducts(Model model) {
        int countProduct = productService.countProducts();
        int countCustomer = customerService.countCustomers();
        int countOrder = orderService.countOrders();
        Double total = productService.getTotalRevenue();

        model.addAttribute("total", total);
        model.addAttribute("countOrder", countOrder);
        model.addAttribute("countProduct", countProduct);
        model.addAttribute("countCustomer", countCustomer);
        return "statistical";
    }

//    @PostMapping("/ShowChartType")
//    public String processForm(@RequestParam("dateFrom") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateFrom,
//                               @RequestParam("dateTo") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateTo,
//                              @RequestParam("chartType") String chartType,
//                              Model model) {
//        List<Object> chartData = orderService.getData(dateFrom, dateTo, chartType);
//
//        if (chartData != null) {
//            if (chartData.isEmpty()) {
//                throw new IllegalArgumentException("No data available for the selected chart type: " + chartType);
//            }
//            Object firstData = chartData.get(0);
//
//            switch (firstData.getClass().getSimpleName()) {
//                case "Customer":
//                    List<Customer> topCustomers = chartData.stream()
//                            .map(c -> (Customer) c)
//                            .collect(Collectors.toList());
//                    model.addAttribute("top5Customers", topCustomers);
//                    break;
//
//                case "Product":
//                    List<Product> topProducts = chartData.stream()
//                            .map(p -> (Product) p)
//                            .collect(Collectors.toList());
//                    model.addAttribute("top10Products", topProducts);
//                    break;
//
//                case "Employee":
//                    List<Employee> topEmployees = chartData.stream()
//                            .map(e -> (Employee) e)
//                            .collect(Collectors.toList());
//                    model.addAttribute("top5Employees", topEmployees);
//                    break;
//
//                case "MonthlyRevenue":
//                    List<MonthlyRevenue> topMonth = chartData.stream()
//                            .map(m -> (MonthlyRevenue) m)
//                            .collect(Collectors.toList());
//                    model.addAttribute("monthlyRevenue", topMonth);
//                    break;
//
//                case "WeeklyRevenue":
//                    List<WeeklyRevenue> topWeek = chartData.stream()
//                            .map(w -> (WeeklyRevenue) w)
//                            .collect(Collectors.toList());
//                    model.addAttribute("weeklyRevenue", topWeek);
//                    break;
//                default:
//                    throw new IllegalArgumentException("Invalid chart type: " + chartType);
//            }
//        } else {
//            throw new IllegalArgumentException("No data available for the selected chart type: " + chartType);
//        }
//        int countProduct = productService.countProducts();
//        int countCustomer = customerService.countCustomers();
//        int countOrder = orderService.countOrders();
//        Double total = productService.getTotalRevenue();
//
//        model.addAttribute("total", total);
//        model.addAttribute("countOrder", countOrder);
//        model.addAttribute("countProduct", countProduct);
//        model.addAttribute("countCustomer", countCustomer);
//        return "statistical";
//    }
}
