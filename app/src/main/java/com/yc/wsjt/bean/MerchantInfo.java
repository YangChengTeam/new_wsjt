package com.yc.wsjt.bean;

/**
 * Created by zhangdinghui on 2019/5/7.
 */
public class MerchantInfo {

    private int id;

    private String merchantName;

    private int merchantImg;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public int getMerchantImg() {
        return merchantImg;
    }

    public void setMerchantImg(int merchantImg) {
        this.merchantImg = merchantImg;
    }
}
