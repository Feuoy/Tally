package com.feuoy.tally.bean;


public class CurveReportBean {

    public enum RecordType {
        RECORD_TYPE_EXPENSE,
        RECORD_TYPE_INCOME
    }

    private String year;    //  年，2019
    private String month;    //  月，01
    private RecordType type;   // 收支类型
    private double amount; //  金额


    public CurveReportBean(String year, String month, RecordType type, double amount) {
        this.year = year;
        this.month = month;
        this.type = type;
        this.amount = amount;
    }


    public int getType() {
        if (this.type == RecordType.RECORD_TYPE_EXPENSE) {
            return 1;
        } else {
            return 2;
        }
    }

    public void setType(int type) {
        if (type == 1) {
            this.type = RecordType.RECORD_TYPE_EXPENSE;
        } else {
            this.type = RecordType.RECORD_TYPE_INCOME;
        }
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }


    @Override
    public String toString() {
        return "CurveReportBean{" +
                "year='" + year + '\'' +
                ", month='" + month + '\'' +
                ", type=" + type +
                ", amount=" + amount +
                '}';
    }

}
