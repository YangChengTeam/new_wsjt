package com.yc.wsjt.bean;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * Created by zhangdinghui on 2019/5/16.
 */
@Entity(tableName = "circle_setting_info", indices = {@Index(value = {"id"}, unique = true)})
public class CircleSettingInfo {

    @PrimaryKey(autoGenerate = true)
    public int id;

    private String roleName;

    private String roleHead;

    private String coverImage;

    private String unreadUserName;

    private String unreadUserHead;

    private int unreadCount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleHead() {
        return roleHead;
    }

    public void setRoleHead(String roleHead) {
        this.roleHead = roleHead;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getUnreadUserName() {
        return unreadUserName;
    }

    public void setUnreadUserName(String unreadUserName) {
        this.unreadUserName = unreadUserName;
    }

    public String getUnreadUserHead() {
        return unreadUserHead;
    }

    public void setUnreadUserHead(String unreadUserHead) {
        this.unreadUserHead = unreadUserHead;
    }

    public int getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(int unreadCount) {
        this.unreadCount = unreadCount;
    }
}
