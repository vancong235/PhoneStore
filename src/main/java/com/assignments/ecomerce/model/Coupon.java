package com.assignments.ecomerce.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.Date;


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

    public Date getStartDay() {
        return startDay;
    }

    public Coupon(String code, int count, String promotion, String description, Integer status, Date startDay, Date endDay) {
        this.code = code;
        this.count = count;
        this.promotion = promotion;
        this.description = description;
        this.status = status;
        this.startDay = startDay;
        this.endDay = endDay;
    }

    public void setStartDay(Date startDay) {
        this.startDay = startDay;
    }

    public Date getEndDay() {
        return endDay;
    }

    public void setEndDay(Date endDay) {
        this.endDay = endDay;
    }

    private Integer status;

    private Date startDay;
    private Date endDay;

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public int getCount() {
        return count;
    }

    public String getPromotion() {
        return promotion;
    }

    public String getDescription() {
        return description;
    }

    public Integer getStatus() {
        return status;
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
