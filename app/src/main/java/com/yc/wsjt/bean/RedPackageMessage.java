package com.yc.wsjt.bean;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by zhangdinghui on 2019/5/16.
 */
@Entity(tableName = "wx_red_package_info",inheritSuperIndices = true)
public class RedPackageMessage extends MessageContent {

    private String messageTime;

    private int redType;//发红包,收红包

    private String redNumber;

    private String otherSideEmojiUrl;

    private String replyEmojiUrl;

    private String redDesc;

    public int getRedType() {
        return redType;
    }

    public void setRedType(int redType) {
        this.redType = redType;
    }

    public String getRedNumber() {
        return redNumber;
    }

    public void setRedNumber(String redNumber) {
        this.redNumber = redNumber;
    }

    public String getOtherSideEmojiUrl() {
        return otherSideEmojiUrl;
    }

    public void setOtherSideEmojiUrl(String otherSideEmojiUrl) {
        this.otherSideEmojiUrl = otherSideEmojiUrl;
    }

    public String getReplyEmojiUrl() {
        return replyEmojiUrl;
    }

    public void setReplyEmojiUrl(String replyEmojiUrl) {
        this.replyEmojiUrl = replyEmojiUrl;
    }

    public String getRedDesc() {
        return redDesc;
    }

    public void setRedDesc(String redDesc) {
        this.redDesc = redDesc;
    }

    public String getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }
}
