package com.assignments.ecomerce.model;

import jakarta.persistence.*;
@Entity
@Table(name = "Permissions")
public class Permissions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Permissions(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Permissions(){}
}
