package com.assignments.ecomerce.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;
@Entity
@Table(name = "Users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String fullName;
    private String userName;
    private String passWord;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Roles> roles;

    private  Integer status;

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public void setRoles(List<Roles> roles) {
        this.roles = roles;
    }

    public Users(Integer id, String userName, String passWord, List<Roles> roles) {
        this.id = id;
        this.userName = userName;
        this.passWord = passWord;
        this.roles = roles;
    }

    public Users(String userName, String passWord, List<Roles> roles) {
        this.userName = userName;
        this.passWord = passWord;
        this.roles = roles;
    }

    public Users(){}

    public Integer getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public List<Roles> getRoles() {
        return roles;
    }

    public Integer getStatus() {
        return status;
    }
}
