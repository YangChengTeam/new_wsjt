package com.yc.wsjt.bean;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * Created by zhangdinghui on 2019/5/31.
 */
@Entity(tableName = "circle_info", indices = {@Index(value = {"id"}, unique = true)})
public class CircleInfo {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String deviceId;

    private String userName;

    private String userHead;

    private String content;

    private String circleImages;//通过#号拼接图片地址

    private String sendDate;

    private String address;

    private String praiseInfo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCircleImages() {
        return circleImages;
    }

    public void setCircleImages(String circleImages) {
        this.circleImages = circleImages;
    }

    public String getSendDate() {
        return sendDate;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPraiseInfo() {
        return praiseInfo;
    }

    public void setPraiseInfo(String praiseInfo) {
        this.praiseInfo = praiseInfo;
    }
}
