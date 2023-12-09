package com.assignments.ecomerce.service;

import com.assignments.ecomerce.model.*;
import com.assignments.ecomerce.repository.OrderDetailRepository;
import com.assignments.ecomerce.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.*;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private ForgotPasswordService emailService;

    public Orders findById(Integer id) {
        return orderRepository.findById(id).get();
    }

    public List<Orders> getAllOrders() {
        return (List<Orders>) orderRepository.findAll();
    }


    public void saveOrder(Orders orders) {
        orderRepository.save(orders);
    }

    public List<Orders> get5RecentlyAddedOrders() {
        return orderRepository.get5RecentlyAddedOrders();

    }

    public Orders getOrderById(Integer id) {
        Optional<Orders> optionalOrder = orderRepository.findById(id);
        return optionalOrder.orElse(null);
    }

    public double convertVNDToUSD(double vndAmount) {
        double exchangeRate = 23000;
        double usdAmount = vndAmount / exchangeRate;

        DecimalFormat decimalFormat = new DecimalFormat("#.0");
        String formattedAmount = decimalFormat.format(usdAmount);
        return Double.parseDouble(formattedAmount);
    }

    public List<Double> getMonthlyRevenue(int year) {
        List<Object[]> getMonthlyRevenue = orderRepository.getMonthlyRevenue(year);
        List<Double> Data = new ArrayList<>();
        for (Object[] result : getMonthlyRevenue) {
            Double temp = (Double) result[2];
            Double total = convertVNDToUSD(temp);
            Data.add(total);
        }
        return Data;
    }

    public int countOrders() {
        return orderRepository.countOrders();
    }

    public Page<Orders> pageOrders(int pageNo) {
        Pageable pageable = PageRequest.of(pageNo, 5);
        return orderRepository.pageOrders(pageable);
    }


    public Page<Orders> searchOrders(int pageNo, String keyword, int pageSize) {

        String status1 = "Chờ xác nhận";
        String status2 = "Đã xác nhận";
        String status3 = "Đã hủy đơn";
        if (status1.toLowerCase().replaceAll("\\s", "").contains(keyword.toLowerCase()) && keyword != "") {
            keyword = "1";
        }
        if (status2.toLowerCase().replaceAll("\\s", "").contains(keyword.toLowerCase()) && keyword != "") {
            keyword = "2";
        }
        if (status3.toLowerCase().replaceAll("\\s", "").contains(keyword.toLowerCase()) && keyword != "") {
            keyword = "0";
        }
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        List<Orders> order = transfer(orderRepository.searchOrders(keyword.trim()));
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
            newOrder.setEmployee(order.getEmployee());
            orderList.add(newOrder);
        }
        return orderList;
    }

    public String update(Orders orders) {
        Orders orderUpdate = null;
        try {
            orderUpdate = orderRepository.findById(orders.getId()).get();
            orderUpdate.setEmployee(orders.getEmployee());
            orderUpdate.setShipName(orders.getShipName());
            orderUpdate.setShipPhoneNumber(orders.getShipPhoneNumber());
            orderUpdate.setStatus(2);
            //update order status
            orderRepository.save(orderUpdate);

            //set quantity product
            List<OrderDetail> orderDetails = orderDetailRepository.findListProductByOrderId(orderUpdate.getId());
            for (OrderDetail od : orderDetails) {
                Product product = productService.getProductById(od.getProduct().getId());
                int quan = product.getQuantity();
                int newQuan = quan - od.getQuantity();
                if (newQuan < 0) {
                    //update order status
                    orderUpdate.setStatus(1);
                    orderRepository.save(orderUpdate);
                    String message = "Sản phẩm với ID: " + product.getId() + " không đủ số lượng. \n\n Vui lòng kiểm tra số lượng sản phẩm trước khi xác nhận ";
                    return message;
                }
                product.setQuantity(newQuan);
                productService.updateQuantity(product);
            }
            //send email confirm
            emailService.sendEmailConfirmOrder(orderUpdate.getCustomer().getEmail(),
                    "Order Confirmation",
                    orderUpdate.getEmployee().getName(),
                    orderDetails);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public void cancelOrder(Integer id) {
        try {
            Orders orders = orderRepository.getById(id);
            orders.setStatus(0);
            orderRepository.save(orders);

            emailService.sendEmailCancelOrder(orders.getCustomer().getEmail(),
                    "Order Cancel",
                    orders.getEmployee().getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Page<Orders> searchOrdersByTotalPrice(Integer pageNo, String start, String end) {
        Double priceStart = 0.0;
        Double priceEnd = Double.MAX_VALUE;
        if (start != null && start != "") {
            priceStart = Double.parseDouble(start.trim().replace(",", ""));
        }
        if (end != null && end != "") {
            priceEnd = Double.parseDouble(end.trim().replace(",", ""));
        }

        List<Orders> order = transfer(orderRepository.searchOrdersByTotalPrice(priceStart, priceEnd));
        int pageSize = order.size() > 1 ? order.size() : 1;
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Orders> orderPages = toPage(order, pageable);
        return orderPages;
    }

    public Page<Orders> searchOrdersByDate(Integer pageNo, Date start, Date end) {
        if (start == null) {
            start = new Date(0);
        }
        if (end == null) {
            end = Calendar.getInstance().getTime();
        }
        List<Orders> order = transfer(orderRepository.searchOrdersByDate(start, end));
        int pageSize = order.size() > 1 ? order.size() : 1;
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Orders> orderPages = toPage(order, pageable);
        return orderPages;
    }
}
