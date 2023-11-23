package com.assignments.ecomerce.dto;

import com.assignments.ecomerce.model.Roles;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Data
@NoArgsConstructor @AllArgsConstructor
public class UserDTO {
    private String fullName;
    private String userName;
    private String passWord;
    private String repeatPassword;

    @ManyToOne
    @JoinColumn(name = "roleId")
    private Roles role;

    public void setRole(Roles role) {
        this.role = role;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
