package com.yc.wsjt.bean;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * Created by zhangdinghui on 2019/4/19.
 */

@Entity(tableName = "quick_info", indices = {@Index(value = {"id","localImg","icon"}, unique = true)})
public class QuickInfo {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String icon;

    private String img;
    private String val;

    private int type;
    private int sort;
    private int status;
    private String recommond;
    private int vip;
    private int localImg;

    private Long addDate;

    @Ignore
    private boolean isEdit;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRecommond() {
        return recommond;
    }

    public void setRecommond(String recommond) {
        this.recommond = recommond;
    }

    public int getVip() {
        return vip;
    }

    public void setVip(int vip) {
        this.vip = vip;
    }

    public int getLocalImg() {
        return localImg;
    }

    public void setLocalImg(int localImg) {
        this.localImg = localImg;
    }

    public boolean isEdit() {
        return isEdit;
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
    }

    public Long getAddDate() {
        return addDate;
    }

    public void setAddDate(Long addDate) {
        this.addDate = addDate;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }
}
