package com.assignments.ecomerce.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class OrderDetailId implements Serializable {
    @Column(insertable = false, updatable = false)
    private Integer orderId;

    @Column(insertable = false, updatable = false)
    private Integer productId;

    public OrderDetailId(Integer orderId, Integer productId) {
        this.orderId = orderId;
        this.productId = productId;
    }

    public OrderDetailId() {

    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }


}
