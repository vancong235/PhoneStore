package com.assignments.ecomerce.model;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "Category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public Integer getStatus() {
        return status;
    }

    @ManyToOne
    @JoinColumn(name = "supplierId")
    private Supplier supplier;

    private Integer status;
    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category(String name,Integer status) {
        this.name = name;
        this.status = status;
    }

    public Category() {
    }
}
