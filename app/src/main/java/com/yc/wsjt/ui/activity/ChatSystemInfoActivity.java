package com.yc.wsjt.ui.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jaeger.library.StatusBarUtil;
import com.yc.wsjt.App;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.MessageContent;
import com.yc.wsjt.bean.SystemTipsMessage;
import com.yc.wsjt.bean.WeixinChatInfo;
import com.yc.wsjt.presenter.Presenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhangdinghui on 2019/5/8.
 */
public class ChatSystemInfoActivity extends BaseActivity {

    @BindView(R.id.btn_config)
    Button mConfigBtn;

    @BindView(R.id.et_system_info)
    EditText mSystemInfoEt;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_system_info;
    }

    @Override
    protected Presenter getPresenter() {
        return null;
    }

    @Override
    protected void initVars() {
        StatusBarUtil.setLightMode(this);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @OnClick(R.id.btn_recall)
    void recall() {
        mSystemInfoEt.setText(getResources().getString(R.string.sys_recall));
        mSystemInfoEt.setSelection(getResources().getString(R.string.sys_recall).length());
    }

    @OnClick(R.id.btn_add_friend)
    void addFriend() {
        mSystemInfoEt.setText(getResources().getString(R.string.sys_add_friend));
        mSystemInfoEt.setSelection(getResources().getString(R.string.sys_add_friend).length());
    }

    @OnClick(R.id.btn_say_hello)
    void sayHello() {
        mSystemInfoEt.setText(getResources().getString(R.string.sys_say_hello));
        mSystemInfoEt.setSelection(getResources().getString(R.string.sys_say_hello).length());
    }

    @OnClick(R.id.btn_stranger)
    void stranger() {
        mSystemInfoEt.setText(getResources().getString(R.string.sys_stranger));
        mSystemInfoEt.setSelection(getResources().getString(R.string.sys_stranger).length());
    }

    @OnClick(R.id.btn_config)
    void config() {

        if (StringUtils.isEmpty(mSystemInfoEt.getText())) {
            ToastUtils.showLong("请输入提示语");
            return;
        }

        int type = MessageContent.SYSTEM_TIPS;

        //插入一条时间设置记录
        SystemTipsMessage systemTipsMessage = new SystemTipsMessage();
        systemTipsMessage.setWxMainId(App.getApp().getMessageContent().getWxMainId());
        systemTipsMessage.setMessageContent(mSystemInfoEt.getText().toString());
        systemTipsMessage.setMessageType(type);
        Long sysId = mAppDatabase.systemTipsMessageDao().insert(systemTipsMessage);

        //插入到外层的列表中
        WeixinChatInfo weixinChatInfo = new WeixinChatInfo();
        weixinChatInfo.setWxMainId(App.getApp().getMessageContent().getWxMainId());
        weixinChatInfo.setTypeIcon(R.mipmap.type_system_info);
        weixinChatInfo.setType(type);
        weixinChatInfo.setChildTabId(sysId);

        mAppDatabase.weixinChatInfoDao().insert(weixinChatInfo);

        finish();
    }
}
