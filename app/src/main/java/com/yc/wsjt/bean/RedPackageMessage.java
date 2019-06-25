package com.yc.wsjt.bean;

import androidx.room.Entity;

/**
 * Created by zhangdinghui on 2019/5/16.
 */
@Entity(tableName = "wx_red_package_info",inheritSuperIndices = true)
public class RedPackageMessage extends MessageContent {

    private String messageTime;

    private int redType = 1;//发红包,收红包

    private boolean isReceive;

    private int redCount;//红包个数

    private String redNumber;

    private String otherSideEmojiUrl;

    private String replyEmojiUrl;

    private String redDesc;

    private int receiveType = 1; //1,别人收我的红包，2,我收别人的红包

    private String otherSideName;//对方人昵称

    private String otherSideHead;//对方人头像

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

    public boolean isReceive() {
        return isReceive;
    }

    public void setReceive(boolean receive) {
        isReceive = receive;
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

    public int getRedCount() {
        return redCount;
    }

    public void setRedCount(int redCount) {
        this.redCount = redCount;
    }

    public int getReceiveType() {
        return receiveType;
    }

    public void setReceiveType(int receiveType) {
        this.receiveType = receiveType;
    }

    public String getOtherSideName() {
        return otherSideName;
    }

    public void setOtherSideName(String otherSideName) {
        this.otherSideName = otherSideName;
    }

    public String getOtherSideHead() {
        return otherSideHead;
    }

    public void setOtherSideHead(String otherSideHead) {
        this.otherSideHead = otherSideHead;
    }
}
