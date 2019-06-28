package com.yc.wsjt.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by zhangdinghui on 2019/6/4.
 */
public class HomeDataWrapper {

    private List<BannerInfo> banner;

    private List<QuickInfo> tool;

    public HelpUrl urls;

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

    public HelpUrl getUrls() {
        return urls;
    }

    public void setUrls(HelpUrl urls) {
        this.urls = urls;
    }

    public class HelpUrl {
        @SerializedName("new_help")
        public String newHelp;

        @SerializedName("kf_help")
        public String kfHelp;

        public String getNewHelp() {
            return newHelp;
        }

        public void setNewHelp(String newHelp) {
            this.newHelp = newHelp;
        }

        public String getKfHelp() {
            return kfHelp;
        }

        public void setKfHelp(String kfHelp) {
            this.kfHelp = kfHelp;
        }
    }
}
