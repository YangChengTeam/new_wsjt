package com.yc.wsjt.bean;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "my_emoji_info", indices = {@Index(value = {"id"}, unique = true)})
public class EmojiInfo {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String icon;

    private int sort;

    private int status;

    private String localEmoji;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getLocalEmoji() {
        return localEmoji;
    }

    public void setLocalEmoji(String localEmoji) {
        this.localEmoji = localEmoji;
    }
}
