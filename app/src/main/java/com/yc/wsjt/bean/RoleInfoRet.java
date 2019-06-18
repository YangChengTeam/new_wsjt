package com.yc.wsjt.bean;

import java.util.List;

/**
 * Created by myflying on 2018/11/16.
 */
public class RoleInfoRet extends ResultInfo {

    private List<RoleInfo> data;

    public List<RoleInfo> getData() {
        return data;
    }

    public void setData(List<RoleInfo> data) {
        this.data = data;
    }
}
