package com.yc.wsjt.bean;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * Created by zhangdinghui on 2019/5/31.
 */
@Entity(tableName = "circle_comment_info", indices = {@Index(value = {"id"}, unique = true)})
public class CommentInfo {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public int circleId;//朋友圈单条记录的ID

    private String sendUserName;

    private String replyUserName;

    private String replyContent;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCircleId() {
        return circleId;
    }

    public void setCircleId(int circleId) {
        this.circleId = circleId;
    }

    public String getSendUserName() {
        return sendUserName;
    }

    public void setSendUserName(String sendUserName) {
        this.sendUserName = sendUserName;
    }

    public String getReplyUserName() {
        return replyUserName;
    }

    public void setReplyUserName(String replyUserName) {
        this.replyUserName = replyUserName;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }
}
