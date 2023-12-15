package com.assignments.ecomerce.repository;

import com.assignments.ecomerce.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail,Integer>{
//    List<OrderDetail> findAllByOrderId(Integer orderId);

    @Query(value = "SELECT * FROM OrderDetail", nativeQuery = true)
    List<OrderDetail> findAll();
    @Query(value = "SELECT * FROM OrderDetail WHERE orderId = :orderId", nativeQuery = true)
    List<OrderDetail> findListProductByOrderId(@Param("orderId") Integer orderId);

    @Query(value = "SELECT orderdetail.productId, SUM(orderdetail.quantity) AS total_sold FROM orderdetail " +
            "JOIN `orders` ON orderdetail.orderId = `orders`.Id WHERE YEAR(`orders`.orderDate) = :year " +
            "GROUP BY orderdetail.productId " +
            "ORDER BY total_sold " +
            "DESC LIMIT 5",nativeQuery = true)
    List<Object[]> get5TopSaleProducts(int year);

    @Modifying
    @Query(value = "INSERT INTO orderdetail (orderId, productId, quantity, unitPrice, isComment) VALUES (:orderId, :productId, :quantity, :unitPrice, :isComment)", nativeQuery = true)
    int saveOrderDetail(@Param("orderId") Integer orderId, @Param("productId") Integer productId, @Param("quantity") Integer quantity, @Param("unitPrice") Double unitPrice, @Param("isComment") Boolean isComment);

    @Query(value = "SELECT o.id FROM orders o, orderdetail od WHERE o.customerId = :customerId AND od.productId = :productId LIMIT 1", nativeQuery = true)
    int findOrderIdBought(@Param("customerId") Integer customerId, @Param("productId") Integer productId);
}
