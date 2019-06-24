package com.yc.wsjt.bean;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * Created by zhangdinghui on 2019/6/12.
 */
@Entity(tableName = "chat_role_info", indices = {@Index(value = {"id"}, unique = true)})
public class RoleInfo {
    @PrimaryKey(autoGenerate = true)
    private Long id;

    private String nickname;

    private int sex;

    private int avatarLocalImg;

    private String avatar;

    private Long qunLiaoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getAvatarLocalImg() {
        return avatarLocalImg;
    }

    public void setAvatarLocalImg(int avatarLocalImg) {
        this.avatarLocalImg = avatarLocalImg;
    }

    public Long getQunLiaoId() {
        return qunLiaoId;
    }

    public void setQunLiaoId(Long qunLiaoId) {
        this.qunLiaoId = qunLiaoId;
    }
}
