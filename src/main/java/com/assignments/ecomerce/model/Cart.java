package com.assignments.ecomerce.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cart")
public class Cart {


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<CartDetail> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartDetail> cartItems) {
        this.cartItems = cartItems;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public Cart(Integer userId, List<CartDetail> cartItems, double totalPrice, int totalItems) {

        this.userId = userId;
        this.cartItems = cartItems;
        this.totalPrice = totalPrice;
        this.totalItems = totalItems;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer userId;

    @OneToMany(cascade = CascadeType.DETACH, mappedBy = "Cart")
    private List<CartDetail> cartItems;
    private double totalPrice;
    private int totalItems;

    public Cart() {
        this.cartItems = new ArrayList<>();
        this.totalItems = 0;
        this.totalPrice = 0.0;
    }
}
