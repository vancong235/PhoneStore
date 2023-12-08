package com.assignments.ecomerce.repository;

import com.assignments.ecomerce.model.Orders;
import com.assignments.ecomerce.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders,Integer> {
    @Query(value = "SELECT COUNT(*) FROM orders", nativeQuery = true)
    int countOrders();

    @Query(value = "SELECT * FROM Orders  Order BY id DESC   LIMIT 5 ",nativeQuery = true)
    List<Orders> get5RecentlyAddedOrders ();
    @Query("SELECT p from Orders p")
    Page<Orders> pageOrders(Pageable pageable);

//    @Query("SELECT c.name, c.phoneNumber, c.address, c.email, SUM(od.quantity) AS sumQuantity " +
//            "FROM OrderDetail od " +
//            "JOIN od.order o " +
//            "JOIN o.customer c " +
//            "WHERE o.orderDate BETWEEN :dateFrom AND :dateTo " +
//            "GROUP BY c.name, c.phoneNumber, c.address, c.email " +
//            "ORDER BY sumQuantity DESC")
//    List<Object[]> getTop5CustomersWithSumQuantity(@Param("dateFrom") Date dateFrom, @Param("dateTo") Date dateTo);
//
//    @Query("SELECT p.name, p.price, p.description, p.quantity, p.size, SUM(od.quantity) AS sumQuantity " +
//            "FROM OrderDetail od " +
//            "JOIN od.product p " +
//            "JOIN od.order o " +
//            "WHERE o.orderDate BETWEEN :dateFrom AND :dateTo " +
//            "GROUP BY p.name, p.price, p.description, p.quantity, p.size " +
//            "ORDER BY sumQuantity DESC")
//        List<Object[]> getTop10ProductsWithSumQuantity(@Param("dateFrom") Date dateFrom, @Param("dateTo") Date dateTo);
//
//    @Query("SELECT e.name, e.phoneNumber, e.address, e.email , e.salary , SUM(od.quantity) AS totalQuantity " +
//            "FROM OrderDetail od " +
//            "JOIN od.employee e " +
//            "JOIN od.order o " +
//            "WHERE o.orderDate BETWEEN :dateFrom AND :dateTo " +
//            "GROUP BY e.name, e.phoneNumber, e.address, e.email, e.salary " +
//            "ORDER BY totalQuantity DESC")
//    List<Object[]> findTop5EmployeesByTotalQuantity(@Param("dateFrom") Date dateFrom, @Param("dateTo") Date dateTo);
//

    @Query("SELECT MONTH(o.orderDate) AS month, YEAR(o.orderDate) AS year, SUM(o.total) AS sumTotal " +
            "FROM Orders o " +
            "WHERE o.orderDate BETWEEN :startDate AND :endDate " +
            "GROUP BY MONTH(o.orderDate), YEAR(o.orderDate) " +
            "ORDER BY sumTotal DESC")
    List<Object[]> getMonthlyRevenue(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query(value = "SELECT temp.month, IFNULL(o.year, :userYear) AS year, COALESCE(o.sumTotal, 0.0) AS sumTotal " +
            "FROM (" +
            "    SELECT 1 AS month UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL " +
            "    SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL " +
            "    SELECT 9 UNION ALL SELECT 10 UNION ALL SELECT 11 UNION ALL SELECT 12 " +
            ") AS temp " +
            "LEFT JOIN (" +
            "    SELECT MONTH(o.orderDate) AS month, YEAR(o.orderDate) AS year, SUM(o.total) AS sumTotal " +
            "    FROM Orders o " +
            "    WHERE YEAR(o.orderDate) = :userYear " +
            "    GROUP BY MONTH(o.orderDate), YEAR(o.orderDate) " +
            ") AS o ON temp.month = o.month " +
            "ORDER BY temp.month ASC", nativeQuery = true)
    List<Object[]> getMonthlyRevenue(@Param("userYear") int userYear);
//
//    @Query("SELECT CONCAT('Tuần',WEEK(o.orderDate), ' - ', " +
//            "DATE_FORMAT(MIN(o.orderDate),'%d/%m/%Y'), ' - ', " +
//            "DATE_FORMAT(MAX(o.orderDate),'%d/%m/%Y')) AS weekDate, " +
//            "SUM(CASE WHEN DAYOFWEEK(o.orderDate) = 2 THEN o.total ELSE 0 END) AS Monday, " +
//            "SUM(CASE WHEN DAYOFWEEK(o.orderDate) = 3 THEN o.total ELSE 0 END) AS Tuesday, " +
//            "SUM(CASE WHEN DAYOFWEEK(o.orderDate) = 4 THEN o.total ELSE 0 END) AS Wednesday, " +
//            "SUM(CASE WHEN DAYOFWEEK(o.orderDate) = 5 THEN o.total ELSE 0 END) AS Thursday, " +
//            "SUM(CASE WHEN DAYOFWEEK(o.orderDate) = 6 THEN o.total ELSE 0 END) AS Friday, " +
//            "SUM(CASE WHEN DAYOFWEEK(o.orderDate) = 7 THEN o.total ELSE 0 END) AS Saturday, " +
//            "SUM(CASE WHEN DAYOFWEEK(o.orderDate) = 1 THEN o.total ELSE 0 END) AS Sunday " +
//            "FROM Orders o " +
//            "WHERE o.orderDate BETWEEN :startDate AND :endDate " +
//            "GROUP BY WEEK(o.orderDate) " +
//            "ORDER BY WEEK(o.orderDate)")
//    List<Object[]> getWeeklyRevenue(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
//
    @Query("SELECT p from Orders p where CONCAT(p.customer.name,p.status,p.employee.name) like %?1%")
    List<Orders> searchOrders(String keyword);

    @Query("SELECT p from Orders p where p.total >= :start and p.total<= :end ")
    List<Orders> searchOrdersByTotalPrice(Double  start, Double  end);

    @Query("SELECT p from Orders p where p.orderDate >= :start and p.orderDate <= :end ")
    List<Orders> searchOrdersByDate(Date start, Date end);
}
