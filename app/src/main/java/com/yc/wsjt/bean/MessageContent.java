package com.yc.wsjt.bean;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * Created by zhangdinghui on 2019/5/16.
 */
@Entity(tableName = "wx_chat_info_main",indices = {@Index(value = {"id","deviceId"}, unique = true)})
public class MessageContent {

    //时间
    public static final int CHAT_DATE = 0;

    //文本
    public static final int SEND_TEXT = 1;
    public static final int RECEIVE_TEXT = 2;

    //图片
    public static final int SEND_IMAGE = 3;
    public static final int RECEIVE_IMAGE = 4;

    //图片中视频类型
    public static final int SEND_IMAGE_FOR_VIDEO = 5;
    public static final int RECEIVE_IMAGE_FOR_VIDEO = 6;

    //语音
    public static final int SEND_VOICE = 7;
    public static final int RECEIVE_VOICE = 8;

    //表情
    public static final int SEND_EMOJI = 9;
    public static final int RECEIVE_EMOJI = 10;

    //红包
    public static final int SEND_RED_PACKET = 11;
    public static final int RECEIVE_RED_PACKET = 12;

    //转账
    public static final int SEND_TRANSFER = 13;
    public static final int RECEIVE_TRANSFER = 14;

    //视频
    public static final int SEND_VIDEO = 15;
    public static final int RECEIVE_VIDEO = 16;

    //转发
    public static final int SEND_SHARE = 17;
    public static final int RECEIVE_SHARE = 18;

    //个人名片
    public static final int SEND_PERSON_CARD = 19;
    public static final int RECEIVE_PERSON_CARD = 20;

    //邀请加群
    public static final int SEND_JOIN_GROUP = 21;
    public static final int RECEIVE_JOIN_GROUP = 22;

    //系统提示
    public static final int SYSTEM_TIPS = 23;

    @PrimaryKey(autoGenerate = true)
    public int id;

    public int wxMainId;

    public String deviceId;

    @Ignore
    private UserInfo userInfo;

    private String messageUserName;

    private String messageUserHead;

    private int messageType;

    private int localMessageImg;

    private String messageContent;

    private int sort;

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWxMainId() {
        return wxMainId;
    }

    public void setWxMainId(int wxMainId) {
        this.wxMainId = wxMainId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public int getLocalMessageImg() {
        return localMessageImg;
    }

    public void setLocalMessageImg(int localMessageImg) {
        this.localMessageImg = localMessageImg;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getMessageUserName() {
        return messageUserName;
    }

    public void setMessageUserName(String messageUserName) {
        this.messageUserName = messageUserName;
    }

    public String getMessageUserHead() {
        return messageUserHead;
    }

    public void setMessageUserHead(String messageUserHead) {
        this.messageUserHead = messageUserHead;
    }

}
