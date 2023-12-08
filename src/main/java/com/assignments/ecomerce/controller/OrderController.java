package com.assignments.ecomerce.controller;

import com.assignments.ecomerce.config.Config;
import com.assignments.ecomerce.model.*;

import com.assignments.ecomerce.service.CartDetailService;
import com.assignments.ecomerce.service.OrderService;
import com.assignments.ecomerce.service.ProductService;
import com.assignments.ecomerce.service.UserService;

import com.assignments.ecomerce.service.*;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.Principal;

import java.text.DecimalFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Controller
public class OrderController {
    @Autowired
    private CartDetailService cartDetailService;
    @Autowired
    private ProductService productService;
    @Autowired
    private CustomerService customerService;

    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private CouponService couponService;
    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private EmployeeService employeeService;


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
    public String orderProduct(@RequestParam(value = "promo", defaultValue = "UNDEFINED") String promo, Model model, Principal principal) {
        System.out.println(promo);
        if (principal != null) {
            Users user = userService.findByEmail(principal.getName());

            List<CartDetail> list = cartDetailService.findByUserId(user.getId());
            Double sum = 0.0d;
            int size = 0;
            Double promoPrice = 0.0d;
            for (CartDetail object : list) {
                object.setProduct(productService.getProductById(object.getProductId()));
                sum += (object.getProduct().getPrice() - (object.getProduct().getPrice() * object.getProduct().getDiscount() / 100)) * object.getQuantity();
            }
            size = list.size();
            if (!promo.equals("UNDEFINED")) {
                try {
                    Coupon coupon = couponService.findByCodeReturnObject(promo);
                    model.addAttribute("status", coupon.getDescription());
                    promoPrice=Integer.parseInt(coupon.getPromotion())*sum/100;
                } catch (Exception e) {
                    model.addAttribute("status", "Mã không tồn tại");
                }
            } else {
                model.addAttribute("status", "Mã không tồn tại");
            }
            DecimalFormat df = new DecimalFormat(",###,###");
            model.addAttribute("size", size);
            model.addAttribute("promo", promo);
            model.addAttribute("promoPrice", df.format(Math.floor(promoPrice)));
            model.addAttribute("totalPrice", df.format(Math.floor(sum-promoPrice)));
            model.addAttribute("listCartDetail", list);
            model.addAttribute("userId", user.getId());
            model.addAttribute("email", principal.getName());
            model.addAttribute("name", user.getFullname());

            return "checkout";
        } else {
            // User is not logged in, redirect to login page
            return "redirect:/login";
        }
    }
    @GetMapping("/return")
    public String returnPayment(HttpServletRequest request,Principal principal, Model model) throws UnsupportedEncodingException {
        if (principal != null) {
            Map fields = new HashMap();
            for (Enumeration params = request.getParameterNames(); params.hasMoreElements(); ) {
                String fieldName = URLEncoder.encode((String) params.nextElement(), StandardCharsets.US_ASCII.toString());
                String fieldValue = URLEncoder.encode(request.getParameter(fieldName), StandardCharsets.US_ASCII.toString());
                if ((fieldValue != null) && (fieldValue.length() > 0)) {
                    fields.put(fieldName, fieldValue);
                }
            }

            String vnp_SecureHash = request.getParameter("vnp_SecureHash");
            if (fields.containsKey("vnp_SecureHashType")) {
                fields.remove("vnp_SecureHashType");
            }
            if (fields.containsKey("vnp_SecureHash")) {
                fields.remove("vnp_SecureHash");
            }
            String signValue = Config.hashAllFields(fields);

            if (signValue.equals(vnp_SecureHash)) {
                if ("00".equals(request.getParameter("vnp_ResponseCode"))) {
                    model.addAttribute("message", 1);
                    Users users = userService.findByEmail(principal.getName());
                    Customer customer = customerService.findByEmail(principal.getName());
                    List<CartDetail> listCartDetail = cartDetailService.findByUserId(users.getId());
                    List<OrderDetail> listOrderDetail = null;
                    Orders order = new Orders();
                    order.setId(Integer.parseInt(request.getParameter("vnp_TxnRef")));
                    order.setCustomer(customer);
                    order.setPaymentMethod("VNPay");
                    String dateString = request.getParameter("vnp_PayDate");
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                    try {
                        Date date = dateFormat.parse(dateString);
                        order.setOrderDate(date);
                        order.setStatus(1);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    order.setTotal(Double.valueOf(request.getParameter("vnp_Amount")));
                    for (CartDetail cartDetail : listCartDetail) {
                        OrderDetail orderDetail = new OrderDetail();
                        OrderDetailId orderDetailId = new OrderDetailId();
                        orderDetailId.setOrderId(order.getId());
                        orderDetailId.setProductId(cartDetail.getProductId());
                        orderDetail.setQuantity(cartDetail.getQuantity());
                        orderDetail.setUnitPrice(cartDetail.getUnitPrice());
                        orderDetail.setComment(false);
                        listOrderDetail.add(orderDetail);
                    }
                    orderService.saveOrder(order);
                    for (OrderDetail orderDetail : listOrderDetail) {
                        orderDetailService.save(orderDetail);
                    }
                } else {
                    model.addAttribute("message", 0);
                }
            } else {
                model.addAttribute("message", 2);
            }
            // Trả về tên view để hiển thị dữ liệu
            return "vnpay_return";
        } else {
            return "Vui lòng đăng nhập";
        }
    }

    @GetMapping("/vnpay_pay")
    public String paymentWithVNpay(Model model, Principal principal) {
        return "vnpay_pay";
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
