package com.yc.wsjt.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by zhangdinghui on 2019/5/30.
 */
public class WeiXinPayInfo implements MultiItemEntity {

    public static final int PERSON = 1;

    public static final int MERCHANT = 2;

    public static final int RECEIVE_CODE = 3;

    public static final int MONEY_START = 4;

    public static final int MONEY_END = 5;

    public static final int RECEIVE_MERCHANT = 6;

    private String title;//支付标题

    private String merchantHead;//商户头像

    private double payMoney;//付款金额

    private String receiveName;//收款方

    private String receiveMessage;//付款留言

    private String payState;//交易状态

    private double receiveMoney;//收款金额

    private String payerRemark;//付款方备注

    private String summary;//汇总

    private String receiveRemark;//备注

    private String takeLaunchDate;//提现发起时间

    private double takeMoney;//提现金额

    private String takeBank;//提现银行

    private String takeDate;//提现时间

    private String estimateDate;//预计到账时间

    private String arrivalDate;//到账时间

    private String arrivalRemark;//到账备注

    private String entryDate;//入账时间

    private double entryMoney;//入账金额

    private String entryAccount;//入账账户

    private String merchantName;//付款商户

    private String entryDetail;//入账详情

    private int itemType;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMerchantHead() {
        return merchantHead;
    }

    public void setMerchantHead(String merchantHead) {
        this.merchantHead = merchantHead;
    }

    public double getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(double payMoney) {
        this.payMoney = payMoney;
    }

    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    public String getReceiveMessage() {
        return receiveMessage;
    }

    public void setReceiveMessage(String receiveMessage) {
        this.receiveMessage = receiveMessage;
    }

    public String getPayState() {
        return payState;
    }

    public void setPayState(String payState) {
        this.payState = payState;
    }

    public double getReceiveMoney() {
        return receiveMoney;
    }

    public void setReceiveMoney(double receiveMoney) {
        this.receiveMoney = receiveMoney;
    }

    public String getPayerRemark() {
        return payerRemark;
    }

    public void setPayerRemark(String payerRemark) {
        this.payerRemark = payerRemark;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getReceiveRemark() {
        return receiveRemark;
    }

    public void setReceiveRemark(String receiveRemark) {
        this.receiveRemark = receiveRemark;
    }

    public String getTakeLaunchDate() {
        return takeLaunchDate;
    }

    public void setTakeLaunchDate(String takeLaunchDate) {
        this.takeLaunchDate = takeLaunchDate;
    }

    public double getTakeMoney() {
        return takeMoney;
    }

    public void setTakeMoney(double takeMoney) {
        this.takeMoney = takeMoney;
    }

    public String getTakeBank() {
        return takeBank;
    }

    public void setTakeBank(String takeBank) {
        this.takeBank = takeBank;
    }

    public String getTakeDate() {
        return takeDate;
    }

    public void setTakeDate(String takeDate) {
        this.takeDate = takeDate;
    }

    public String getEstimateDate() {
        return estimateDate;
    }

    public void setEstimateDate(String estimateDate) {
        this.estimateDate = estimateDate;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getArrivalRemark() {
        return arrivalRemark;
    }

    public void setArrivalRemark(String arrivalRemark) {
        this.arrivalRemark = arrivalRemark;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public double getEntryMoney() {
        return entryMoney;
    }

    public void setEntryMoney(double entryMoney) {
        this.entryMoney = entryMoney;
    }

    public String getEntryAccount() {
        return entryAccount;
    }

    public void setEntryAccount(String entryAccount) {
        this.entryAccount = entryAccount;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getEntryDetail() {
        return entryDetail;
    }

    public void setEntryDetail(String entryDetail) {
        this.entryDetail = entryDetail;
    }

    public WeiXinPayInfo(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
