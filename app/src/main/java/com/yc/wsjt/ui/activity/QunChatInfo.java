package com.yc.wsjt.ui.activity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.yc.wsjt.bean.RoleInfo;

import java.util.List;

@Entity(tableName = "qun_chat_info", indices = {@Index(value = {"deviceId"}, unique = true)})
public class QunChatInfo {

    @PrimaryKey(autoGenerate = true)
    public Long id;

    public String deviceId;

    @Ignore
    private List<RoleInfo> roleList;

    private String chatInfoBg;

    private String qunLiaoName;

    private int chatPersonNumber;

    private boolean isOpenDisturb;

    private boolean openNickName;//显示群群员昵称

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public List<RoleInfo> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<RoleInfo> roleList) {
        this.roleList = roleList;
    }

    public String getChatInfoBg() {
        return chatInfoBg;
    }

    public void setChatInfoBg(String chatInfoBg) {
        this.chatInfoBg = chatInfoBg;
    }

    public String getQunLiaoName() {
        return qunLiaoName;
    }

    public void setQunLiaoName(String qunLiaoName) {
        this.qunLiaoName = qunLiaoName;
    }

    public int getChatPersonNumber() {
        return chatPersonNumber;
    }

    public void setChatPersonNumber(int chatPersonNumber) {
        this.chatPersonNumber = chatPersonNumber;
    }

    public boolean isOpenDisturb() {
        return isOpenDisturb;
    }

    public void setOpenDisturb(boolean openDisturb) {
        isOpenDisturb = openDisturb;
    }

    public boolean isOpenNickName() {
        return openNickName;
    }

    public void setOpenNickName(boolean openNickName) {
        this.openNickName = openNickName;
    }
}
