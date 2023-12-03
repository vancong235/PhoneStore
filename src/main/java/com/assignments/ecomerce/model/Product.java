package com.assignments.ecomerce.model;

import jakarta.persistence.*;
import lombok.Getter;
@Entity
@Table(name = "Product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Double price;
    private Integer quantity;
    private String image;
    private String description;
    private String size;

    public Product(Integer id, String name, Double price, Integer quantity, String image, String description, String size, Integer discount, Category category, Integer status) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.image = image;
        this.description = description;
        this.size = size;
        this.discount = discount;
        this.category = category;
        this.status = status;
    }

    private Integer discount;

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;

    private  Integer status;

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Product(String name) {
        this.name = name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(Category category) {
        this.category = category;
    }



    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }



    public Category getCategory() {
        return category;
    }

    public Integer getStatus() {
        return status;
    }

    public Product() {

    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Product(String name, Double price, Integer quantity, String image, String description, String size, Category category) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.image = image;
        this.description = description;
        this.size = size;
        this.category = category;
    }

    public Product(String name, Double price, Integer quantity, String description, String size,Integer discount ) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.size = size;
        this.discount = discount;
    }

    public Product(String name, Double price, Integer quantity, String description, String size,String image) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.size = size;
        this.image = image;
    }
    @Override
    public String toString() {
        return "{" +
                "\"id\": " + id +
                ", \"name\": \"" + name.toString().trim().replace("\n", "") + "\"" +
                ", \"price\": " + price +
                ", \"quantity\": " + quantity +
                ", \"image\": \"" + image + "\"" +
                "}";
    }
}

