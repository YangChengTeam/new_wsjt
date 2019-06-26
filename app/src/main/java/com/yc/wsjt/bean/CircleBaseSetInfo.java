package com.yc.wsjt.bean;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * Created by zhangdinghui on 2019/5/16.
 */
@Entity(tableName = "circle_base_set_info", indices = {@Index(value = {"id"}, unique = true)})
public class CircleBaseSetInfo {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String deviceId;

    private String roleName;

    private String roleHead;

    private String coverImage;

    private String unreadUserName;

    private String unreadUserHead;

    private String unreadCount;

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

    public String getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(String unreadCount) {
        this.unreadCount = unreadCount;
    }
}
