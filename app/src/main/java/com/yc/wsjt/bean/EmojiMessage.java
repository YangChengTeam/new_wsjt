package com.yc.wsjt.bean;

import androidx.room.Entity;
import androidx.room.Ignore;

/**
 * Created by zhangdinghui on 2019/5/16.
 */
@Entity(tableName = "wx_emoji_info",inheritSuperIndices = true)
public class EmojiMessage extends MessageContent {

    @Ignore
    public int defUrl;

    private String emojiUrl;

    public int getDefUrl() {
        return defUrl;
    }

    public void setDefUrl(int defUrl) {
        this.defUrl = defUrl;
    }

    public String getEmojiUrl() {
        return emojiUrl;
    }

    public void setEmojiUrl(String emojiUrl) {
        this.emojiUrl = emojiUrl;
    }
}
