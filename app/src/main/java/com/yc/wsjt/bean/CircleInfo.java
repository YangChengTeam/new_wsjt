package com.yc.wsjt.bean;

import java.util.List;

/**
 * Created by zhangdinghui on 2019/5/31.
 */
public class CircleInfo {
    private String id;
    private String userName;
    private int userHead;
    private String content;
    private List<String> circleImages;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserHead() {
        return userHead;
    }

    public void setUserHead(int userHead) {
        this.userHead = userHead;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getCircleImages() {
        return circleImages;
    }

    public void setCircleImages(List<String> circleImages) {
        this.circleImages = circleImages;
    }
}
