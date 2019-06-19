package com.yc.wsjt.bean;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by zhangdinghui on 2019/4/19.
 * 主要用于记录聊天的列表数据信息
 */
@Entity(tableName = "weixin_chat_info")
public class WeixinChatInfo {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public int wxMainId;

    private int typeIcon;

    public int type;

    public String chatText;

    public Long childTabId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTypeIcon() {
        return typeIcon;
    }

    public void setTypeIcon(int typeIcon) {
        this.typeIcon = typeIcon;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getChatText() {
        return chatText;
    }

    public void setChatText(String chatText) {
        this.chatText = chatText;
    }

    public int getWxMainId() {
        return wxMainId;
    }

    public void setWxMainId(int wxMainId) {
        this.wxMainId = wxMainId;
    }

    public Long getChildTabId() {
        return childTabId;
    }

    public void setChildTabId(Long childTabId) {
        this.childTabId = childTabId;
    }
}
