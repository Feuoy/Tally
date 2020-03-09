package com.feuoy.tally.bean;

public class ClassReportBean {

    private String year_month;    //  年-月，2019-08
    private String category;   // 分类名
    private double amount; //  金额


    public ClassReportBean(String year_month, String category, double amount) {
        this.year_month = year_month;
        this.category = category;
        this.amount = amount;
    }


    public String getYear_month() {
        return year_month;
    }

    public void setYear_month(String year_month) {
        this.year_month = year_month;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }


    @Override
    public String toString() {
        return "ClassReportBean{" +
                "year_month='" + year_month + '\'' +
                ", category='" + category + '\'' +
                ", amount=" + amount +
                '}';
    }

}
