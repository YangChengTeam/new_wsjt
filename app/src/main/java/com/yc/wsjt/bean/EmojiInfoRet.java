package com.yc.wsjt.bean;

import java.util.List;

/**
 * Created by myflying on 2018/11/16.
 */
public class EmojiInfoRet extends ResultInfo {

    private List<EmojiInfo> data;

    public List<EmojiInfo> getData() {
        return data;
    }

    public void setData(List<EmojiInfo> data) {
        this.data = data;
    }
}
