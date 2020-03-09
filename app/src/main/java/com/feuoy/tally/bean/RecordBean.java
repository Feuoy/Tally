package com.feuoy.tally.bean;

import com.feuoy.tally.util.DateUtil;

import java.io.Serializable;
import java.util.UUID;


public class RecordBean implements Serializable {

    // 收支类型，1 支出 2 收入
    public enum RecordType {
        RECORD_TYPE_EXPENSE,
        RECORD_TYPE_INCOME
    }

    private double amount;  // 金额
    private RecordType type;    // 收支类型
    private String category;    // 分类名称
    private String remark;  // 备注
    private String date;    //  日期，yyyy-MM-dd
    private long timeStamp; // 时间戳
    private String uuid;    // uuid


    public RecordBean() {
        date = DateUtil.getNowYearMonthDay();
        timeStamp = System.currentTimeMillis();
        uuid = UUID.randomUUID().toString();
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }


    @Override
    public String toString() {
        return "RecordBean{" +
                "amount=" + amount +
                ", type=" + type +
                ", category='" + category + '\'' +
                ", remark='" + remark + '\'' +
                ", date='" + date + '\'' +
                ", timeStamp=" + timeStamp +
                ", uuid='" + uuid + '\'' +
                '}';
    }

}
