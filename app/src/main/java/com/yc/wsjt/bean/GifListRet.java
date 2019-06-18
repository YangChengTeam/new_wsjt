package com.yc.wsjt.bean;

import java.util.List;

/**
 * Created by myflying on 2018/11/16.
 */
public class GifListRet extends ResultInfo {

    private List<GifInfo> data;

    public List<GifInfo> getData() {
        return data;
    }

    public void setData(List<GifInfo> data) {
        this.data = data;
    }
}
