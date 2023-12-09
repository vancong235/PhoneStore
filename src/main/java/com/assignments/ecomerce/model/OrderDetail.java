package com.assignments.ecomerce.model;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name="OrderDetail")
public class OrderDetail {

    @EmbeddedId
    private OrderDetailId id;

    public OrderDetailId getId() {
        return id;
    }

    public void setId(OrderDetailId id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "orderId")
    private Orders order;
    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;

    private Integer quantity;
    private Double unitPrice;

    private Boolean isComment;

    public OrderDetail(OrderDetailId id, Orders order, Product product, Boolean isComment, Integer quantity, Double unitPrice) {
        this.id = id;
        this.order = order;
        this.product = product;
        this.isComment = isComment;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public Boolean getComment() {
        return isComment;
    }

    public void setComment(Boolean comment) {
        isComment = comment;
    }


    public Orders getOrder() {
        return order;
    }

    public Product getProduct() {
        return product;
    }


    public Integer getQuantity() {
        return quantity;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public OrderDetail(){}


    public void setOrder(Orders order) {
        this.order = order;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }


    public OrderDetail(Orders order, Product product, Boolean isComment, Integer quantity, Double unitPrice) {
        this.order = order;
        this.product = product;
        this.isComment = isComment;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public OrderDetail(Orders order, Product product, Integer quantity, Double unitPrice) {
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }
}
