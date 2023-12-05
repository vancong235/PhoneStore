package com.assignments.ecomerce.controller;

import com.assignments.ecomerce.model.CartDetail;
import com.assignments.ecomerce.model.Category;
import com.assignments.ecomerce.model.Product;
import com.assignments.ecomerce.model.Users;
import com.assignments.ecomerce.service.CartDetailService;
import com.assignments.ecomerce.service.ProductService;
import com.assignments.ecomerce.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@RestController
@RequestMapping("/api")
public class CartDetailController {
    @Autowired
    private CartDetailService cartDetailService;
    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @GetMapping(value = "/index", produces = "application/json")
    public String index(Model model, Principal principal, @RequestParam("userId") int userId) {
        if (principal != null && principal.getName() != null) {
            Users user = userService.findByEmail(principal.getName());
            if (user != null && user.getId() == userId) {
                List<CartDetail> list = cartDetailService.findByUserId(userId);
                List<Product> listProduct = new ArrayList<>();
                double multi = 0;
                double quantity = 0;

                JsonArray jsonArray = new JsonArray();

                for (CartDetail cartDetail : list) {
                    Product product = productService.findById(cartDetail.getProductId());
                    if (product != null) {
                        multi += cartDetail.getQuantity() * product.getPrice();
                        quantity = cartDetail.getQuantity();
                        listProduct.add(product);
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("product", product.toString());
                        jsonObject.addProperty("multi", multi);
                        jsonObject.addProperty("quantity", quantity);
                        jsonArray.add(jsonObject);
                    }
                }

                Gson gson = new Gson();
                String json = gson.toJson(jsonArray);

                return json;
            } else {
                // Người dùng không tồn tại, trả về lỗi hoặc thông báo không tìm thấy
                return "error: User not found";
            }
        } else {
            // Principal rỗng, trả về lỗi hoặc thông báo không có xác thực
            return "error: Unauthorized";
        }
    }

    @PostMapping("/delete")
    public String delete(HttpServletRequest request, Model model, Principal principal) {
        int userId = Integer.parseInt(request.getParameter("userId"));
        if (principal != null && principal.getName() != null) {
            Users user = userService.findByEmail(principal.getName());
            if (user != null && user.getId() == userId) {
                int productId = Integer.parseInt(request.getParameter("productId"));
                String message = "Lỗi xóa sản phẩm! Quý khách vui lòng thử lại sau ít phút!";
                if (cartDetailService.deleteCart(user.getId(), productId)) {
                    message = "Quý khách đã xóa thành công mã sản phẩm " + productId;
                }
                return message;
            } else {
                // Người dùng không tồn tại, trả về lỗi hoặc thông báo không tìm thấy
                return "error: User not found";
            }
        } else {
            // Principal rỗng, trả về lỗi hoặc thông báo không có xác thực
            return "error: Unauthorized";
        }
    }

    @PostMapping("/add")
    public String add(HttpServletRequest request, Model model, Principal principal) {
        int userId = Integer.parseInt(request.getParameter("userId"));
        if (principal != null && principal.getName() != null) {
            Users user = userService.findByEmail(principal.getName());
            if (user != null && user.getId() == userId) {
                int productId = Integer.parseInt(request.getParameter("productId"));
                Product product = productService.findById(productId);
                String message = "Thêm thất bại";
                if (product != null) {
                    if (cartDetailService.saveCart(user.getId(), productId, 1, product.getPrice())) {
                        message = "Thêm thành công mã sản phẩm " + product.getId() + " vào giỏ hàng";
                    }
                } else {
                    message = "Sản phẩm không tồn tại";
                }
                return message;
            } else {
                // Người dùng không tồn tại, trả về lỗi hoặc thông báo không tìm thấy
                return "error: User not found";
            }
        } else {
            // Principal rỗng, trả về lỗi hoặc thông báo không có xác thực
            return "error: Unauthorized";
        }
    }
}

//        Lưu thông tin sản phẩm 150 của user 1 đã xử lý logic trùng, tăng 1
//        cartDetailService.saveCart(1,150,1, 1);

//        Xóa sản phẩm 150 của user 1
//        cartDetailService.deleteCart(1,150);