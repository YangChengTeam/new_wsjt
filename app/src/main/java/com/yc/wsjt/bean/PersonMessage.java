package com.yc.wsjt.bean;

import androidx.room.Entity;

/**
 * Created by zhangdinghui on 2019/5/16.
 */
@Entity(tableName = "wx_person_info", inheritSuperIndices = true)
public class PersonMessage extends MessageContent {

    private String personName;

    private int defPersonHeadImg;

    private String personHeadImg;

    private String weixinNumber;

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public int getDefPersonHeadImg() {
        return defPersonHeadImg;
    }

    public void setDefPersonHeadImg(int defPersonHeadImg) {
        this.defPersonHeadImg = defPersonHeadImg;
    }

    public String getPersonHeadImg() {
        return personHeadImg;
    }

    public void setPersonHeadImg(String personHeadImg) {
        this.personHeadImg = personHeadImg;
    }

    public String getWeixinNumber() {
        return weixinNumber;
    }

    public void setWeixinNumber(String weixinNumber) {
        this.weixinNumber = weixinNumber;
    }
}
