package com.assignments.ecomerce.model;

import jakarta.persistence.*;
import lombok.Getter;
@Entity
@Table(name = "Roles")
public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Roles(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Roles(){}

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
