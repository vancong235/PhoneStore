package com.assignments.ecomerce.service;

import com.assignments.ecomerce.model.Coupon;
import com.assignments.ecomerce.repository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CouponService {
    @Autowired
    private CouponRepository couponRepository;

    public List<Coupon> getAllCoupons() {
        return (List<Coupon>) couponRepository.findAll();
    }

    public Coupon save(Coupon coupon) {
        coupon.setStatus(1);
        return couponRepository.save(coupon);
    }

    public Page<Coupon> pageCoupon(int pageNo) {
        Pageable pageable = PageRequest.of(pageNo, 4);
        return couponRepository.pageCoupon(pageable);
    }

    public Coupon findById(Integer id) {
        return couponRepository.findById(id).get();
    }

    public String findByCode(String code){
        return couponRepository.findByCode(code);
    }
    public Coupon update(Coupon coupon) {
        Coupon couponSave = null;
        try {
            couponSave = couponRepository.findById(coupon.getId()).get();
            couponSave.setCode(coupon.getCode());
            couponSave.setCount(coupon.getCount());
            couponSave.setPromotion(coupon.getPromotion());
            couponSave.setDescription(coupon.getDescription());
            couponSave.setStartDay(coupon.getStartDay());
            couponSave.setEndDay(coupon.getEndDay());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return couponRepository.save(couponSave);
    }

    public void deleteById(Integer id) {
        Coupon coupon = couponRepository.getById(id);
        coupon.setStatus(0);
        couponRepository.save(coupon);
    }

    public void enableById(Integer id) {
        Coupon coupon = couponRepository.getById(id);
        couponRepository.save(coupon);
    }

    public Page<Coupon> searchCoupon(int pageNo, String keyword) {
        Pageable pageable = PageRequest.of(pageNo, 4);
        List<Coupon> Coupons = transfer(couponRepository.searchCoupon(keyword.trim()));
        Page<Coupon> couponPages = toPage(Coupons, pageable);
        return couponPages;
    }

    private Page toPage(List<Coupon> list, Pageable pageable) {
        if (pageable.getOffset() >= list.size()) {
            return Page.empty();
        }
        int startIndex = (int) pageable.getOffset();
        int endIndex = ((pageable.getOffset() + pageable.getPageSize()) > list.size())
                ? list.size() : (int) (pageable.getOffset() + pageable.getPageSize());
        List subList = list.subList(startIndex, endIndex);
        return new PageImpl(subList, pageable, list.size());
    }

    public List<Coupon> transfer(List<Coupon> coupons) {
        List<Coupon> CouponList = new ArrayList<>();
        for (Coupon coupon : coupons) {
            Coupon newCoupon = new Coupon();
            newCoupon.setId(coupon.getId());
            newCoupon.setCode(coupon.getCode());
            newCoupon.setCount(coupon.getCount());
            newCoupon.setPromotion(coupon.getPromotion());
            newCoupon.setDescription(coupon.getDescription());
            newCoupon.setStartDay(coupon.getStartDay());
            newCoupon.setEndDay(coupon.getEndDay());
            CouponList.add(newCoupon);
        }
        return CouponList;
    }

    public Coupon findByCodeReturnObject(String promo) {
        return couponRepository.findByCodeReturnObject(promo);
    }
}
