package com.yc.wsjt.bean;

import java.util.List;

/**
 * Created by zhangdinghui on 2019/6/4.
 */
public class HomeDataWrapper {
    private List<BannerInfo> banner;
    private List<QuickInfo> tool;

    public List<BannerInfo> getBanner() {
        return banner;
    }

    public void setBanner(List<BannerInfo> banner) {
        this.banner = banner;
    }

    public List<QuickInfo> getTool() {
        return tool;
    }

    public void setTool(List<QuickInfo> tool) {
        this.tool = tool;
    }
}
