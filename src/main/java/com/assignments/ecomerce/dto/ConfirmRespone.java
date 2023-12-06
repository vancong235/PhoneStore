package com.assignments.ecomerce.dto;

import com.assignments.ecomerce.model.Employee;

public class ConfirmRespone {

    private Integer orderId;
    private Employee employee;

    public ConfirmRespone() {
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public ConfirmRespone(Integer orderId, Employee employee) {
        this.orderId = orderId;
        this.employee = employee;
    }
}
