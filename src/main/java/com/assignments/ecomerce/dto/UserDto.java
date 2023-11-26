package com.assignments.ecomerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Data
public class UserDto {
    private String email;
    private String password;
    private String role;
    private String fullname;
    private Integer status;

    public UserDto() {
    }

    public UserDto(String fullname, String email, String password, String role) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.fullname = fullname;
        this.status = 1;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
}

