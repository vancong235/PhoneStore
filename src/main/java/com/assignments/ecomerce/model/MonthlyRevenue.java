package com.assignments.ecomerce.model;

public class MonthlyRevenue {
    private int month;
    private int year;
    private Double sumTotal;

    public MonthlyRevenue(int month, int year, Double sumTotal) {
        this.month = month;
        this.year = year;
        this.sumTotal = sumTotal;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Double getSumTotal() {
        return sumTotal;
    }

    public void setSumTotal(Double sumTotal) {
        this.sumTotal = sumTotal;
    }
}
