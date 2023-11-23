package com.assignments.ecomerce.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Entity
@Table(name="Orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "customerId")
    private Customer customer;
    private Date orderDate;
    private String status;
    private Integer couponId;
    private String paymentMethod;
    private Double total;
    private String ShipName;
    private String ShipAddress;
    private String ShipPhoneNumber;

    @OneToMany(mappedBy = "order")
    private List<OrderDetail> orderDetails;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCouponId(Integer couponId) {
        this.couponId = couponId;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setShipName(String shipName) {
        ShipName = shipName;
    }

    public void setShipAddress(String shipAddress) {
        ShipAddress = shipAddress;
    }

    public void setShipPhoneNumber(String shipPhone) {
        ShipPhoneNumber = shipPhone;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public Orders(Integer id, Customer customer, Date orderDate, String status, Integer couponId, String paymentMethod, Double total, String shipName, String shipAddress, String shipPhone) {
        this.id = id;
        this.customer = customer;
        this.orderDate = orderDate;
        this.status = status;
        this.couponId = couponId;
        this.paymentMethod = paymentMethod;
        this.total = total;
        ShipName = shipName;
        ShipAddress = shipAddress;
        ShipPhoneNumber = shipPhone;
    }

    public Orders(){}

    public Integer getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public String getStatus() {
        return status;
    }

    public Integer getCouponId() {
        return couponId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public Double getTotal() {
        return total;
    }

    public String getShipName() {
        return ShipName;
    }

    public String getShipAddress() {
        return ShipAddress;
    }

    public String getShipPhoneNumber() {
        return ShipPhoneNumber;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }
}
