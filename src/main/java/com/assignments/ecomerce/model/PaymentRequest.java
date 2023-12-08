package com.assignments.ecomerce.model;

import jakarta.servlet.http.HttpServletRequest;

public class PaymentRequest {
    private int amount;
    private String bankCode;
    private String language;
    private HttpServletRequest httpServletRequest;

    public PaymentRequest() {
    }

    public PaymentRequest(int amount, String bankCode, String language, HttpServletRequest httpServletRequest) {
        this.amount = amount;
        this.bankCode = bankCode;
        this.language = language;
        this.httpServletRequest = httpServletRequest;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public HttpServletRequest getHttpServletRequest() {
        return httpServletRequest;
    }

    public void setHttpServletRequest(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }
}