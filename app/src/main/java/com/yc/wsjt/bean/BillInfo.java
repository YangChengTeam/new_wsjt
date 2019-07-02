package com.yc.wsjt.bean;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * Created by zhangdinghui on 2019/6/12.
 */
@Entity(tableName = "bill_info", indices = {@Index(value = {"id"}, unique = true)})
public class BillInfo {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    private int billImage;

    private String billImageUrl;

    private String billTitle;

    private String billDate;

    private String billMoney;

    private int billType;

    private String yearMonth;

    private boolean isRefund;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public int getBillImage() {
        return billImage;
    }

    public void setBillImage(int billImage) {
        this.billImage = billImage;
    }

    public String getBillImageUrl() {
        return billImageUrl;
    }

    public void setBillImageUrl(String billImageUrl) {
        this.billImageUrl = billImageUrl;
    }

    public String getBillTitle() {
        return billTitle;
    }

    public void setBillTitle(String billTitle) {
        this.billTitle = billTitle;
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    public String getBillMoney() {
        return billMoney;
    }

    public void setBillMoney(String billMoney) {
        this.billMoney = billMoney;
    }

    public int getBillType() {
        return billType;
    }

    public void setBillType(int billType) {
        this.billType = billType;
    }

    public String getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
    }

    public boolean isRefund() {
        return isRefund;
    }

    public void setRefund(boolean refund) {
        isRefund = refund;
    }
}
