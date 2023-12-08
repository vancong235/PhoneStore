package com.assignments.ecomerce.service;

import com.assignments.ecomerce.dto.ProductDTO;
import com.assignments.ecomerce.model.OrderDetail;
import com.assignments.ecomerce.model.Product;
import com.assignments.ecomerce.repository.OrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderDetailService {
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private ProductService productService;

    public List<OrderDetail> getAllOrderDetail() {
        return (List<OrderDetail>) orderDetailRepository.findAll();
    }

//    public List<OrderDetail> findAllByOrderId(Integer orderId) {
//        System.out.println(orderId);
//        return orderDetailRepository.findAllByOrderId(orderId);
//    }

    public List<OrderDetail> findListProductByOrderId(Integer orderId) {
        return orderDetailRepository.findListProductByOrderId(orderId);
    }


    public void save(OrderDetail orderDetail) {
        orderDetailRepository.save(orderDetail);
    }
        public List<Product> getTop5ProductSale ( int year){
            List<Object[]> list = orderDetailRepository.get5TopSaleProducts(year);
            List<Product> listProduct = new ArrayList<>();
            for (Object[] result : list) {
                Product product = productService.getById((Integer) result[0]);
                BigDecimal quantity = (BigDecimal) result[1];
                product.setQuantity(quantity.intValue());
                listProduct.add(product);
            }
            return listProduct;

        }

}
