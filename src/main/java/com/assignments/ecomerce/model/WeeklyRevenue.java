package com.assignments.ecomerce.model;

public class WeeklyRevenue {
    private String weekDate;
    private Double mondayTotal;
    private Double tuesdayTotal;
    private Double wednesdayTotal;
    private Double thursdayTotal;
    private Double fridayTotal;
    private Double saturdayTotal;
    private Double sundayTotal;

    public WeeklyRevenue(String weekDate, Double mondayTotal, Double tuesdayTotal, Double wednesdayTotal,
                         Double thursdayTotal, Double fridayTotal, Double saturdayTotal, Double sundayTotal) {
        this.weekDate = weekDate;
        this.mondayTotal = mondayTotal;
        this.tuesdayTotal = tuesdayTotal;
        this.wednesdayTotal = wednesdayTotal;
        this.thursdayTotal = thursdayTotal;
        this.fridayTotal = fridayTotal;
        this.saturdayTotal = saturdayTotal;
        this.sundayTotal = sundayTotal;
    }

    public String getWeekDate() {
        return weekDate;
    }

    public void setWeekDate(String weekDate) {
        this.weekDate = weekDate;
    }

    public Double getMondayTotal() {
        return mondayTotal;
    }

    public void setMondayTotal(Double mondayTotal) {
        this.mondayTotal = mondayTotal;
    }

    public Double getTuesdayTotal() {
        return tuesdayTotal;
    }

    public void setTuesdayTotal(Double tuesdayTotal) {
        this.tuesdayTotal = tuesdayTotal;
    }

    public Double getWednesdayTotal() {
        return wednesdayTotal;
    }

    public void setWednesdayTotal(Double wednesdayTotal) {
        this.wednesdayTotal = wednesdayTotal;
    }

    public Double getThursdayTotal() {
        return thursdayTotal;
    }

    public void setThursdayTotal(Double thursdayTotal) {
        this.thursdayTotal = thursdayTotal;
    }

    public Double getFridayTotal() {
        return fridayTotal;
    }

    public void setFridayTotal(Double fridayTotal) {
        this.fridayTotal = fridayTotal;
    }

    public Double getSaturdayTotal() {
        return saturdayTotal;
    }

    public void setSaturdayTotal(Double saturdayTotal) {
        this.saturdayTotal = saturdayTotal;
    }

    public Double getSundayTotal() {
        return sundayTotal;
    }

    public void setSundayTotal(Double sundayTotal) {
        this.sundayTotal = sundayTotal;
    }
}
