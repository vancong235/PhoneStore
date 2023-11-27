package com.assignments.ecomerce.service;

import com.assignments.ecomerce.model.*;
import com.assignments.ecomerce.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public Orders findById(Integer id) {
        return orderRepository.findById(id).get();
    }

    public List<Orders> getAllOrders() {
        return (List<Orders>) orderRepository.findAll();
    }

    public Orders getOrderById(Integer id) {
        Optional<Orders> optionalOrder = orderRepository.findById(id);
        return optionalOrder.orElse(null);
    }

    public int countOrders(){
        return orderRepository.countOrders();
    }

    public Page<Orders> pageOrders(int pageNo) {
        Pageable pageable = PageRequest.of(pageNo, 5);
        return orderRepository.pageOrders(pageable);
    }

    public Page<Orders> searchOrders(int pageNo, String keyword) {
        Pageable pageable = PageRequest.of(pageNo, 5);
        List<Orders> order = transfer(orderRepository.searchOrders(keyword));
        Page<Orders> orderPages = toPage(order, pageable);
        return orderPages;
    }

    private Page toPage(List<Orders> list, Pageable pageable) {
        if (pageable.getOffset() >= list.size()) {
            return Page.empty();
        }
        int startIndex = (int) pageable.getOffset();
        int endIndex = ((pageable.getOffset() + pageable.getPageSize()) > list.size())
                ? list.size() : (int) (pageable.getOffset() + pageable.getPageSize());
        List subList = list.subList(startIndex, endIndex);
        return new PageImpl(subList, pageable, list.size());
    }

    public List<Orders> transfer(List<Orders> orders) {
        List<Orders> orderList = new ArrayList<>();
        for (Orders order : orders) {
            Orders newOrder = new Orders();
            newOrder.setId(order.getId());
            newOrder.setCustomer(order.getCustomer());
            newOrder.setOrderDate(order.getOrderDate());
            newOrder.setStatus(order.getStatus());
            newOrder.setCouponId(order.getCouponId());
            newOrder.setPaymentMethod(order.getPaymentMethod());
            newOrder.setTotal(order.getTotal());
            newOrder.setEmployeeId(order.getEmployeeId());
            orderList.add(newOrder);
        }
        return orderList;
    }

//    public List<Object> getData(Date dateFrom, Date dateTo, String chartType) {
//        switch (chartType) {
//            case "top5Customers":
//                List<Object[]> results = orderRepository.getTop5CustomersWithSumQuantity(dateFrom, dateTo);
//                List<Customer> customers = new ArrayList<>();
//
//                for (Object[] result : results) {
//                    String name = (String) result[0];
//                    String phoneNumber = (String) result[1];
//                    String address = (String) result[2];
//                    String email = (String) result[3];
//                    Date birthday = (Date)  result[4];
//                    Boolean gender = (Boolean) result[5] ;
//                    Long sumQuantity = (Long) result[4];
//                    Customer customer = new Customer(name, phoneNumber, address, email, gender, birthday);
//                    customers.add(customer);
//                }
//                return new ArrayList<>(customers);
//            case "top10Products":
//                List<Object[]> resultProduct = orderRepository.getTop10ProductsWithSumQuantity(dateFrom, dateTo);
//                List<Product> products = new ArrayList<>();
//
//                for (Object[] result : resultProduct) {
//                    String name = (String) result[0];
//                    Double price = (Double) result[1];
//                    Integer quantity = (Integer) result[3];
//                    String description = (String) result[2];
//                    String size = (String) result[4];
//                    Integer discount = (Integer) result[5];
//
//                    Product product = new Product(name, price, quantity, description, size,discount );
//                    products.add(product);
//                }
//                return new ArrayList<>(products);
//            case "top5Employees":
//                List<Object[]> resultEmployee = orderRepository.findTop5EmployeesByTotalQuantity(dateFrom, dateTo);
//                List<Employee> employees = new ArrayList<>();
//                for (Object[] result : resultEmployee) {
//                    String name = (String) result[0];
//                    String phoneNumber = (String) result[1];
//                    String address = (String) result[3];
//                    String email = (String) result[2];
//                    Double salary = (Double) result[4];
//                    Employee employee = new Employee(name,phoneNumber,address,email,salary);
//                    employees.add(employee);
//                }
//                return new ArrayList<>(employees);
//            case "monthlyRevenue":
//                List<Object[]> resultMonth = orderRepository.getMonthlyRevenue(dateFrom, dateTo);
//                List<MonthlyRevenue> monthlyRevenues = new ArrayList<>();
//
//                for (Object[] result : resultMonth) {
//                    Integer month = (Integer) result[0];
//                    Integer year = (Integer) result[1];
//                    Double sumTotal = (Double) result[2];
//
//                    MonthlyRevenue monthlyRevenue = new MonthlyRevenue(month, year, sumTotal);
//                    monthlyRevenues.add(monthlyRevenue);
//                }
//                return new ArrayList<>(monthlyRevenues);
//            case "weeklyRevenue":
//                List<Object[]> result = orderRepository.getWeeklyRevenue(dateFrom, dateTo);
//                List<WeeklyRevenue> weeklyRevenues = new ArrayList<>();
//
//                for (Object[] row : result) {
//                    String weekDate = (String) row[0];
//                    Double mondayTotal = (Double) row[1];
//                    Double tuesdayTotal = (Double) row[2];
//                    Double wednesdayTotal = (Double) row[3];
//                    Double thursdayTotal = (Double) row[4];
//                    Double fridayTotal = (Double) row[5];
//                    Double saturdayTotal = (Double) row[6];
//                    Double sundayTotal = (Double) row[7];
//
//                    WeeklyRevenue weeklyRevenue = new WeeklyRevenue(weekDate, mondayTotal, tuesdayTotal, wednesdayTotal,
//                            thursdayTotal, fridayTotal, saturdayTotal, sundayTotal);
//                    weeklyRevenues.add(weeklyRevenue);
//                }
//                return new ArrayList<>(weeklyRevenues);
//            default:
//                throw new IllegalArgumentException("Invalid chart type: " + chartType);
//        }
//    }
}
