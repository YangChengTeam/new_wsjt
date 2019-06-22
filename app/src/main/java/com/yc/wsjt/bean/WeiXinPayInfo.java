package com.yc.wsjt.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by zhangdinghui on 2019/5/30.
 */
public class WeiXinPayInfo implements MultiItemEntity {

    private int itemType;

    private PayInfo payInfo;

    public WeiXinPayInfo(int itemType) {
        this.itemType = itemType;
    }

    public PayInfo getPayInfo() {
        return payInfo;
    }

    public void setPayInfo(PayInfo payInfo) {
        this.payInfo = payInfo;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
