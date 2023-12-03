package com.assignments.ecomerce.service;

import com.assignments.ecomerce.model.CartDetail;
import com.assignments.ecomerce.repository.CartDetailRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.List;

@Service
public class CartDetailService {
    @Autowired
    private CartDetailRepository cartDetailRepository;

    public List<CartDetail> findAll() {
        return cartDetailRepository.findAll();
    }
    public List<CartDetail> findByUserId(Integer userId) {
        return cartDetailRepository.findByUserId(userId);
    }

    public CartDetail findByUserIdAndProductId(Integer userId, Integer productId) {
        return cartDetailRepository.findByUserIdAndProductId(userId, productId);
    }

    @Transactional
    public boolean saveCart(Integer userId, Integer productId, Integer quantity, Double unitPrice) {
        try {
            // Kiểm tra sự tồn tại của bản ghi với userId và productId
            CartDetail existingCartDetail = cartDetailRepository.findByUserIdAndProductId(userId, productId);
            if (existingCartDetail != null) {
                // Nếu bản ghi đã tồn tại, tăng quantity lên 1
                existingCartDetail.setQuantity(existingCartDetail.getQuantity() + 1);
                cartDetailRepository.save(existingCartDetail);
            } else {
                // Nếu bản ghi không tồn tại, tạo mới CartDetail và lưu vào cơ sở dữ liệu
                CartDetail cartDetail = new CartDetail();
                cartDetail.setUserId(userId);
                cartDetail.setProductId(productId);
                cartDetail.setQuantity(quantity);
                cartDetail.setUnitPrice(unitPrice);
                cartDetailRepository.save(cartDetail);
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Transactional
    public boolean deleteCart(Integer userId, Integer productId) {
        try {
            // Kiểm tra sự tồn tại của bản ghi với userId và productId
            CartDetail existingCartDetail = cartDetailRepository.findByUserIdAndProductId(userId, productId);

            if (existingCartDetail != null) {
                cartDetailRepository.delete(existingCartDetail);
                return true;
            } else {
                return false; // Không tìm thấy bản ghi để xóa
            }
        } catch (Exception e) {
            return false;
        }
    }
}