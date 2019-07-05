package com.yc.wsjt.bean;

import androidx.room.Entity;

/**
 * Created by zhangdinghui on 2019/5/16.
 */
@Entity(tableName = "wx_share_info", inheritSuperIndices = true)
public class ShareMessage extends MessageContent {

    private String shareThumb;

    private int shareThumbLocal;

    private String shareTitle;

    private String shareContent;

    public String getShareTitle() {
        return shareTitle;
    }

    public void setShareTitle(String shareTitle) {
        this.shareTitle = shareTitle;
    }

    public String getShareContent() {
        return shareContent;
    }

    public void setShareContent(String shareContent) {
        this.shareContent = shareContent;
    }

    public String getShareThumb() {
        return shareThumb;
    }

    public void setShareThumb(String shareThumb) {
        this.shareThumb = shareThumb;
    }

    public int getShareThumbLocal() {
        return shareThumbLocal;
    }

    public void setShareThumbLocal(int shareThumbLocal) {
        this.shareThumbLocal = shareThumbLocal;
    }
}
