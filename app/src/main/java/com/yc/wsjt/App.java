package com.yc.wsjt;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;

import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.yc.wsjt.bean.ChatDataInfo;
import com.yc.wsjt.bean.ContactPerson;
import com.yc.wsjt.bean.MessageContent;
import com.yc.wsjt.bean.ModuleInfoWrapper;
import com.yc.wsjt.bean.UserInfo;
import com.yc.wsjt.bean.WeixinChatInfo;
import com.yc.wsjt.common.AppDatabase;

import java.util.List;

/**
 * Created by admin on 2017/4/7.
 */

public class App extends Application {

    protected static App mInstance;

    public static Context applicationContext;

    private AppDatabase mAppDatabase;

    public ChatDataInfo chatDataInfo;

    public static boolean isLoginAuth = false;

    public UserInfo mUserInfo;

    public boolean isLogin;

    public ModuleInfoWrapper moduleInfoWrapper;

    private MessageContent messageContent;

    private ContactPerson tempPerson;

    private List<WeixinChatInfo> chatList;

    public static Context getContext() {
        return applicationContext;
    }

    public App() {
        mInstance = this;
    }

    public static App getApp() {
        if (mInstance != null && mInstance instanceof App) {
            return (App) mInstance;
        } else {
            mInstance = new App();
            mInstance.onCreate();
            return (App) mInstance;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = this;
        mAppDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "android_ws_room_dev.db")
                .allowMainThreadQueries()
                .build();

        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, "");//58edcfeb310c93091c000be2 5965ee00734be40b580001a0

        PlatformConfig.setQQZone("1109363390", "2pjY7V6G1IyH0OFS");
        PlatformConfig.setWeixin("wxe2c9363d50771678", "4489feeeea6ee8737db20f5ecf0319bf");
    }

    public AppDatabase getAppDatabase() {
        return mAppDatabase;
    }

    public UserInfo getmUserInfo() {
        return mUserInfo;
    }

    public void setmUserInfo(UserInfo mUserInfo) {
        this.mUserInfo = mUserInfo;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public MessageContent getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(MessageContent messageContent) {
        this.messageContent = messageContent;
    }

    public ModuleInfoWrapper getModuleInfoWrapper() {
        return moduleInfoWrapper;
    }

    public void setModuleInfoWrapper(ModuleInfoWrapper moduleInfoWrapper) {
        this.moduleInfoWrapper = moduleInfoWrapper;
    }

    public ContactPerson getTempPerson() {
        return tempPerson;
    }

    public void setTempPerson(ContactPerson tempPerson) {
        this.tempPerson = tempPerson;
    }

    public List<WeixinChatInfo> getChatList() {
        return chatList;
    }

    public void setChatList(List<WeixinChatInfo> chatList) {
        this.chatList = chatList;
    }
}
