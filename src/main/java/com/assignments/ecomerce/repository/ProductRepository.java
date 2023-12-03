package com.assignments.ecomerce.repository;

import com.assignments.ecomerce.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {
    @Query("SELECT od.product FROM OrderDetail od GROUP BY od.product.id ORDER BY SUM(od.quantity) DESC")
    List<Product> findTop10ByQuantitySold();

    @Query("SELECT DISTINCT size FROM Product WHERE name = :nameProduct")
    List<String> getAllSizeProduct(@Param("nameProduct") String nameProduct);

    @Query("SELECT COUNT(*) FROM OrderDetail od WHERE od.product.id = :productId")
    int getSoldShoeCount(@Param("productId") Integer productId);


    @Query(value = "SELECT COUNT(*) FROM product", nativeQuery = true)
    int countProducts();

    @Query("SELECT SUM(od.quantity * od.unitPrice) FROM OrderDetail od")
    Double getTotalRevenue();

    @Query("SELECT p from Product p where status = 1")
    Page<Product> pageProduct(Pageable pageable);

    @Query("SELECT p FROM Product p JOIN p.category c WHERE p.status = 1 AND c.id = :categoryId")
    Page<Product> pageProductByCategory(Pageable pageable,@Param("categoryId") Integer categoryId);

    @Query("SELECT p FROM Product p JOIN p.category c " +
            "WHERE p.status = 1 AND c.status = 1 " +
            "AND CONCAT(p.name, p.price, p.quantity, p.description, p.size) LIKE %:keyword%")
    List<Product> searchByKeyword(@Param("keyword") String keyword);

    @Query("select p from Product p inner join Category c ON c.id = p.category.id" +
            " where p.category.name = ?1")
    List<Product> findAllByCategory(String category);

    @Query("SELECT p.name, p.price, p.description, p.quantity, p.size,p.image, SUM(od.quantity) AS sumQuantity " +
            "FROM OrderDetail od " +
            "JOIN od.product p " +
            "JOIN od.order o " +
            "GROUP BY p.name, p.price, p.description, p.quantity, p.size " +
            "ORDER BY sumQuantity DESC")
    List<Object[]> getTop10ProductsWithSumQuantity();

}
