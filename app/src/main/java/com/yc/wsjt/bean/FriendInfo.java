package com.yc.wsjt.bean;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * Created by zhangdinghui on 2019/5/16.
 */
@Entity(tableName = "new_friend_info",indices = {@Index(value = {"id"}, unique = true)})
public class FriendInfo {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public int friendState = 1;//1未接受，2接受

    public String nickName;

    public int localHead;

    public String userHead;

    public String remark;

    @Ignore
    private boolean isReceive;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFriendState() {
        return friendState;
    }

    public void setFriendState(int friendState) {
        this.friendState = friendState;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getLocalHead() {
        return localHead;
    }

    public void setLocalHead(int localHead) {
        this.localHead = localHead;
    }

    public String getUserHead() {
        return userHead;
    }

    public void setUserHead(String userHead) {
        this.userHead = userHead;
    }

    public String getRemark() {
        return remark;
    }

    public boolean isReceive() {
        return isReceive;
    }

    public void setReceive(boolean receive) {
        isReceive = receive;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
