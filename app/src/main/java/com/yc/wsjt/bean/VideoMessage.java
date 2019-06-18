package com.yc.wsjt.bean;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by zhangdinghui on 2019/5/16.
 */
@Entity(tableName = "wx_video_info",inheritSuperIndices = true)
public class VideoMessage extends MessageContent{

    private String messageTime;

    private int chatImageUrl;

    private int chatVideoType;//1视频，2语音

    private String chatTime;

    private int chatState;//1已接通，2已取消，3已拒绝

    public int getChatVideoType() {
        return chatVideoType;
    }

    public void setChatVideoType(int chatVideoType) {
        this.chatVideoType = chatVideoType;
    }

    public String getChatTime() {
        return chatTime;
    }

    public void setChatTime(String chatTime) {
        this.chatTime = chatTime;
    }

    public int getChatState() {
        return chatState;
    }

    public void setChatState(int chatState) {
        this.chatState = chatState;
    }

    public int getChatImageUrl() {
        return chatImageUrl;
    }

    public void setChatImageUrl(int chatImageUrl) {
        this.chatImageUrl = chatImageUrl;
    }

    public String getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }
}
