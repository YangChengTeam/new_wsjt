package com.yc.wsjt.bean;

/**
 * Created by zhangdinghui on 2019/6/4.
 */
public class ModuleInfoRet extends ResultInfo{
    private ModuleInfoWrapper data;

    public ModuleInfoWrapper getData() {
        return data;
    }

    public void setData(ModuleInfoWrapper data) {
        this.data = data;
    }
}
