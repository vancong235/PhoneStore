package com.assignments.ecomerce.controller;

import com.assignments.ecomerce.model.*;
import com.assignments.ecomerce.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class StatisticalController {
    DecimalFormat decimalFormat = new DecimalFormat("#.0");
    @Autowired
    private ProductService productService;

    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private UserService userService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private OrderService orderService;

    @GetMapping("/statistical")
    public String countProducts(Model model, Principal principal) {
        List<Product> recentlyAddedProducts = productService.get5RecentlyAddedProducts();
        List<Orders> recentlyAddedOrders = orderService.get5RecentlyAddedOrders();
        Users user = userService.findByEmail(principal.getName());
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
