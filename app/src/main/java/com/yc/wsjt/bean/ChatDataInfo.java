package com.yc.wsjt.bean;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * Created by zhangdinghui on 2019/4/19.
 * 聊天资料设置
 */
@Entity(tableName = "chat_data_info", indices = {@Index(value = {"deviceId"}, unique = true)})

public class ChatDataInfo {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public int modelType;//微信，支付宝，qq

    public String deviceId;

    public String personName;//自己姓名

    @Ignore
    public int personHeadLocal;

    public String personHead;//自己头像

    public String otherPersonName;//对方聊天人姓名

    @Ignore
    public int otherPersonHeadLocal;

    public String otherPersonHead;//对方聊天人头像

    public String chatBgImage;//聊天背景图片

    public int friendType;//好友状态,0:好友，1已加入黑名单，2已删除好友

    public boolean messageDisturb;//消息免打扰,0默认关闭免打扰,1开启免打扰

    public boolean receiverOpen;//使用听筒模式,0默认关闭，1开启听筒默认

    public boolean showWeiXinMoney = true;//显示微信零钱通,0显示，1关闭

    public boolean fontChange = true;//OPPO字体矫正

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

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPersonHead() {
        return personHead;
    }

    public void setPersonHead(String personHead) {
        this.personHead = personHead;
    }

    public String getOtherPersonHead() {
        return otherPersonHead;
    }

    public void setOtherPersonHead(String otherPersonHead) {
        this.otherPersonHead = otherPersonHead;
    }

    public String getOtherPersonName() {
        return otherPersonName;
    }

    public void setOtherPersonName(String otherPersonName) {
        this.otherPersonName = otherPersonName;
    }

    public String getChatBgImage() {
        return chatBgImage;
    }

    public void setChatBgImage(String chatBgImage) {
        this.chatBgImage = chatBgImage;
    }

    public int getFriendType() {
        return friendType;
    }

    public void setFriendType(int friendType) {
        this.friendType = friendType;
    }

    public boolean isMessageDisturb() {
        return messageDisturb;
    }

    public void setMessageDisturb(boolean messageDisturb) {
        this.messageDisturb = messageDisturb;
    }

    public boolean isReceiverOpen() {
        return receiverOpen;
    }

    public void setReceiverOpen(boolean receiverOpen) {
        this.receiverOpen = receiverOpen;
    }

    public boolean isShowWeiXinMoney() {
        return showWeiXinMoney;
    }

    public void setShowWeiXinMoney(boolean showWeiXinMoney) {
        this.showWeiXinMoney = showWeiXinMoney;
    }

    public boolean isFontChange() {
        return fontChange;
    }

    public void setFontChange(boolean fontChange) {
        this.fontChange = fontChange;
    }

    public int getPersonHeadLocal() {
        return personHeadLocal;
    }

    public void setPersonHeadLocal(int personHeadLocal) {
        this.personHeadLocal = personHeadLocal;
    }

    public int getOtherPersonHeadLocal() {
        return otherPersonHeadLocal;
    }

    public void setOtherPersonHeadLocal(int otherPersonHeadLocal) {
        this.otherPersonHeadLocal = otherPersonHeadLocal;
    }

    public int getModelType() {
        return modelType;
    }

    public void setModelType(int modelType) {
        this.modelType = modelType;
    }
}
