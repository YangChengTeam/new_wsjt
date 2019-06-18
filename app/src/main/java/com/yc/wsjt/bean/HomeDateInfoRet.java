package com.yc.wsjt.bean;

/**
 * Created by zhangdinghui on 2019/6/4.
 */
public class HomeDateInfoRet extends ResultInfo{
    private HomeDataWrapper data;

    public HomeDataWrapper getData() {
        return data;
    }

    public void setData(HomeDataWrapper data) {
        this.data = data;
    }
}
