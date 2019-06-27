package com.yc.wsjt.bean;

import java.util.List;

/**
 * Created by myflying on 2018/11/16.
 */
public class VipInfoRet extends ResultInfo {

    private List<VipInfo> data;

    public List<VipInfo> getData() {
        return data;
    }

    public void setData(List<VipInfo> data) {
        this.data = data;
    }
}
