package com.assignments.ecomerce.model;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "Coupon")
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String code;
    private int count;
    private String promotion;
    private String description;
    private Integer status;

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Coupon(Integer id, String code, int count, String promotion, String description) {
        this.id = id;
        this.code = code;
        this.count = count;
        this.promotion = promotion;
        this.description = description;
    }

    public Coupon(String code, int count, String promotion, String description) {
        this.code = code;
        this.count = count;
        this.promotion = promotion;
        this.description = description;
    }

    public Coupon() {
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setPromotion(String promotion) {
        this.promotion = promotion;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
