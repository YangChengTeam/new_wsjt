package com.yc.wsjt.bean;

import java.util.List;

/**
 * Created by zhangdinghui on 2019/6/4.
 */
public class ModuleInfoWrapper {

    private List<QuickInfo> wx;
    private List<QuickInfo> alipay;
    private List<QuickInfo> qq;

    public List<QuickInfo> getWx() {
        return wx;
    }

    public void setWx(List<QuickInfo> wx) {
        this.wx = wx;
    }

    public List<QuickInfo> getAlipay() {
        return alipay;
    }

    public void setAlipay(List<QuickInfo> alipay) {
        this.alipay = alipay;
    }

    public List<QuickInfo> getQq() {
        return qq;
    }

    public void setQq(List<QuickInfo> qq) {
        this.qq = qq;
    }
}
