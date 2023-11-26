package com.assignments.ecomerce.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "forgot_password_token")
public class ForgotPasswordToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String token;

    @ManyToOne(targetEntity = Users.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private Users user;

    @Column(nullable = false)
    private LocalDateTime expireTime;

    @Column(nullable = false)
    private Boolean isuUsed;

    public Integer getId() {
        return id;
    }

    public ForgotPasswordToken() {
    }

    public ForgotPasswordToken(Integer id, String token, Users user, LocalDateTime expireTime, Boolean isuUsed) {
        this.id = id;
        this.token = token;
        this.user = user;
        this.expireTime = expireTime;
        this.isuUsed = isuUsed;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public LocalDateTime getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }

    public Boolean getIsuUsed() {
        return isuUsed;
    }

    public void setIsuUsed(Boolean isuUsed) {
        this.isuUsed = isuUsed;
    }

}


