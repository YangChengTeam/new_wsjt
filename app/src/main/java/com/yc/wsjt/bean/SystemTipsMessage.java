package com.yc.wsjt.bean;

import androidx.room.Entity;

/**
 * Created by zhangdinghui on 2019/5/16.
 */
@Entity(tableName = "wx_system_tips_info",inheritSuperIndices = true)
public class SystemTipsMessage extends MessageContent {

    private int tipsType;//提示类型,撤回，加好友，打招呼，陌生人

    public int getTipsType() {
        return tipsType;
    }

    public void setTipsType(int tipsType) {
        this.tipsType = tipsType;
    }
}
