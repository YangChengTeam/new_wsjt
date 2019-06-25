package com.yc.wsjt.bean;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * Created by zhangdinghui on 2019/5/16.
 */
@Entity(tableName = "red_record_info", indices = {@Index(value = {"id"}, unique = true)})
public class RedRecordInfo {

    @PrimaryKey(autoGenerate = true)
    public int id;

    private Long redPackageId;

    private String redAmount;

    private String userName;

    private String userHead;

    private String receiveDate;

    private boolean isLuck;//是否是手气最佳

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Long getRedPackageId() {
        return redPackageId;
    }

    public void setRedPackageId(Long redPackageId) {
        this.redPackageId = redPackageId;
    }

    public String getRedAmount() {
        return redAmount;
    }

    public void setRedAmount(String redAmount) {
        this.redAmount = redAmount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserHead() {
        return userHead;
    }

    public void setUserHead(String userHead) {
        this.userHead = userHead;
    }

    public String getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(String receiveDate) {
        this.receiveDate = receiveDate;
    }

    public boolean isLuck() {
        return isLuck;
    }

    public void setLuck(boolean luck) {
        isLuck = luck;
    }
}
