package com.assignments.ecomerce.repository;

import com.assignments.ecomerce.model.Coupon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CouponRepository extends JpaRepository<Coupon,Integer> {
    @Query("SELECT p from Coupon p where status = 1")
    Page<Coupon> pageCoupon(Pageable pageable);

    @Query("SELECT p from Coupon p where CONCAT(p.code,p.promotion,p.description) like %?1%")
    List<Coupon> searchCoupon(String keyword);
}
