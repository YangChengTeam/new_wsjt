package com.yc.wsjt.bean;

import androidx.room.Entity;

/**
 * Created by zhangdinghui on 2019/5/16.
 */
@Entity(tableName = "wx_group_info", inheritSuperIndices = true)
public class GroupMessage extends MessageContent {

    private String groupName;

    private String groupHead;

    private int defGroupHead;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupHead() {
        return groupHead;
    }

    public void setGroupHead(String groupHead) {
        this.groupHead = groupHead;
    }

    public int getDefGroupHead() {
        return defGroupHead;
    }

    public void setDefGroupHead(int defGroupHead) {
        this.defGroupHead = defGroupHead;
    }
}
