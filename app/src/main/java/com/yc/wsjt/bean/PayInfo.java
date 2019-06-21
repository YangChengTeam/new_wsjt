package com.yc.wsjt.bean;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by zhangdinghui on 2019/5/16.
 */
@Entity(tableName = "pay_info", indices = {@Index(value = {"id"}, unique = true)})
public class PayInfo implements MultiItemEntity {
    @Ignore
    public static final int PERSON = 1;

    @Ignore
    public static final int MERCHANT = 2;

    @Ignore
    public static final int RECEIVE_CODE = 3;

    @Ignore
    public static final int MONEY_START = 4;

    @Ignore
    public static final int MONEY_END = 5;

    @Ignore
    public static final int RECEIVE_MERCHANT = 6;

    @PrimaryKey(autoGenerate = true)
    public int id;

    private int payType = 1;//6种支付的类型

    private String noticeDate;//通知时间

    private String payDate;//支付时间

    private String payMoney;//支付金额

    private String payRemark;//付款留言

    private String receiveUserName;//收款方姓名

    private String merchantName;//商户名称

    private int merchantLocalHead;//商户头像本地

    private String merchantHead;//商户头像

    private boolean openMiniApp;//是否开启商户小程序

    private String thisReceiveMoney;//本次收款

    private String totalMoney;//累计收款

    private int totalCount;//累计收款笔数

    private String bankName;//提现银行

    private String getMoneyDate;//提现时间

    private String receiveMoneyDate;//到账时间

    private String intoAccount;//入账账户

    private String intoMoneyRemark;//入账详情

    @Ignore
    private int itemType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public String getNoticeDate() {
        return noticeDate;
    }

    public void setNoticeDate(String noticeDate) {
        this.noticeDate = noticeDate;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public String getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(String payMoney) {
        this.payMoney = payMoney;
    }

    public String getPayRemark() {
        return payRemark;
    }

    public void setPayRemark(String payRemark) {
        this.payRemark = payRemark;
    }

    public String getReceiveUserName() {
        return receiveUserName;
    }

    public void setReceiveUserName(String receiveUserName) {
        this.receiveUserName = receiveUserName;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getMerchantHead() {
        return merchantHead;
    }

    public void setMerchantHead(String merchantHead) {
        this.merchantHead = merchantHead;
    }

    public boolean isOpenMiniApp() {
        return openMiniApp;
    }

    public void setOpenMiniApp(boolean openMiniApp) {
        this.openMiniApp = openMiniApp;
    }

    public String getThisReceiveMoney() {
        return thisReceiveMoney;
    }

    public void setThisReceiveMoney(String thisReceiveMoney) {
        this.thisReceiveMoney = thisReceiveMoney;
    }

    public String getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(String totalMoney) {
        this.totalMoney = totalMoney;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getGetMoneyDate() {
        return getMoneyDate;
    }

    public void setGetMoneyDate(String getMoneyDate) {
        this.getMoneyDate = getMoneyDate;
    }

    public String getReceiveMoneyDate() {
        return receiveMoneyDate;
    }

    public void setReceiveMoneyDate(String receiveMoneyDate) {
        this.receiveMoneyDate = receiveMoneyDate;
    }

    public String getIntoAccount() {
        return intoAccount;
    }

    public void setIntoAccount(String intoAccount) {
        this.intoAccount = intoAccount;
    }

    public String getIntoMoneyRemark() {
        return intoMoneyRemark;
    }

    public void setIntoMoneyRemark(String intoMoneyRemark) {
        this.intoMoneyRemark = intoMoneyRemark;
    }

    public int getMerchantLocalHead() {
        return merchantLocalHead;
    }

    public void setMerchantLocalHead(int merchantLocalHead) {
        this.merchantLocalHead = merchantLocalHead;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public PayInfo(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
