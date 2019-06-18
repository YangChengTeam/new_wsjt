package com.yc.wsjt.bean;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by zhangdinghui on 2019/5/16.
 */
@Entity(tableName = "wx_audio_info",inheritSuperIndices = true)
public class AudioMessage extends MessageContent{

    public String messageTime;

    public int audioTime;

    private boolean isRead;

    private boolean openAudioTurn;

    public String getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public boolean isOpenAudioTurn() {
        return openAudioTurn;
    }

    public void setOpenAudioTurn(boolean openAudioTurn) {
        this.openAudioTurn = openAudioTurn;
    }

    public int getAudioTime() {
        return audioTime;
    }

    public void setAudioTime(int audioTime) {
        this.audioTime = audioTime;
    }
}
