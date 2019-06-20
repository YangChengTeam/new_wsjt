package com.yc.wsjt.bean;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * Created by zhangdinghui on 2019/5/16.
 */
@Entity(tableName = "money_detail",indices = {@Index(value = {"id"}, unique = true)})
public class MoneyDetail {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String addTime;

    public int moneyType = 1;//收入2，支出2

    public String moneyTitle;

    public String money;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public int getMoneyType() {
        return moneyType;
    }

    public void setMoneyType(int moneyType) {
        this.moneyType = moneyType;
    }

    public String getMoneyTitle() {
        return moneyTitle;
    }

    public void setMoneyTitle(String moneyTitle) {
        this.moneyTitle = moneyTitle;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
