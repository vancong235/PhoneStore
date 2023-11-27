package com.assignments.ecomerce.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@EqualsAndHashCode
@Entity
@Table(name = "Customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String phoneNumber;
    private String address;
    private String email;
    private Integer statusCustomer;
    private Boolean gender;
    private Date birthday;

    public void setStatusCustomer(Integer statusCustomer) {
        this.statusCustomer = statusCustomer;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public void setStatus(Integer statusCustomer) {
        this.statusCustomer = statusCustomer;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Customer() {
    }

    public Customer( String name, String phoneNumber, String address, String email, Boolean gender, Date birthday) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.email = email;
        this.statusCustomer = statusCustomer;
        this.gender = gender;
        this.birthday = birthday;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public Integer getStatusCustomer() {
        return statusCustomer;
    }
}
