package com.yc.wsjt.bean;

import java.util.List;

/**
 * Created by myflying on 2018/11/16.
 */
public class HeadInfoRet extends ResultInfo {

    private List<HeadInfo> data;

    public List<HeadInfo> getData() {
        return data;
    }

    public void setData(List<HeadInfo> data) {
        this.data = data;
    }
}
