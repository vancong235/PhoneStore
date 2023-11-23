package com.assignments.ecomerce.model;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name="OrderDetail")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "orderId")
    private Orders order;
    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "employeeId")
    private Employee employee;

    private Integer quantity;
    private Double unitPrice;

    public OrderDetail(){}

    public void setId(Integer id) {
        this.id = id;
    }

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

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public OrderDetail(Integer id, Orders order, Product product, Integer quantity, Double unitPrice) {
        this.id = id;
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }
}
