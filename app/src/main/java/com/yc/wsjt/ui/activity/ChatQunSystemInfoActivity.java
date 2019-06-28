package com.yc.wsjt.ui.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jaeger.library.StatusBarUtil;
import com.yc.wsjt.App;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.MessageContent;
import com.yc.wsjt.bean.SystemTipsMessage;
import com.yc.wsjt.bean.WeixinQunChatInfo;
import com.yc.wsjt.presenter.Presenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhangdinghui on 2019/5/8.
 */
public class ChatQunSystemInfoActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView mTitleTv;

    @BindView(R.id.btn_config)
    Button mConfigBtn;

    @BindView(R.id.et_system_info)
    EditText mSystemInfoEt;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_qun_system_info;
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
        mTitleTv.setText("系统消息");
    }

    @OnClick(R.id.btn_join_qun)
    void recall() {
        mSystemInfoEt.setText(getResources().getString(R.string.join_qun_txt));
        mSystemInfoEt.setSelection(getResources().getString(R.string.join_qun_txt).length());
    }

    @OnClick(R.id.btn_join_by_code)
    void joinByCode() {
        mSystemInfoEt.setText(getResources().getString(R.string.join_code_qun_txt));
        mSystemInfoEt.setSelection(getResources().getString(R.string.join_code_qun_txt).length());
    }

    @OnClick(R.id.btn_recall)
    void reCall() {
        mSystemInfoEt.setText(getResources().getString(R.string.qun_message_recall));
        mSystemInfoEt.setSelection(getResources().getString(R.string.qun_message_recall).length());
    }

    @OnClick(R.id.btn_update_name)
    void updateQunName() {
        mSystemInfoEt.setText(getResources().getString(R.string.update_qun_name_txt));
        mSystemInfoEt.setSelection(getResources().getString(R.string.update_qun_name_txt).length());
    }

    @OnClick(R.id.btn_security)
    void security() {
        mSystemInfoEt.setText(getResources().getString(R.string.security_name_txt));
        mSystemInfoEt.setSelection(getResources().getString(R.string.security_name_txt).length());
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
        //systemTipsMessage.setWxMainId(App.getApp().getMessageContent().getWxMainId());
        systemTipsMessage.setMessageContent(mSystemInfoEt.getText().toString());
        systemTipsMessage.setMessageType(type);
        Long sysId = mAppDatabase.systemTipsMessageDao().insert(systemTipsMessage);

        //插入到外层的列表中
        WeixinQunChatInfo weixinQunChatInfo = new WeixinQunChatInfo();
        weixinQunChatInfo.setMainId(App.getApp().getMessageContent().getId());
        weixinQunChatInfo.setTypeIcon(R.mipmap.type_system_info);
        weixinQunChatInfo.setType(type);
        weixinQunChatInfo.setChildTabId(sysId);
        mAppDatabase.weixinQunChatInfoDao().insert(weixinQunChatInfo);

        finish();
    }

    @OnClick(R.id.iv_back)
    void back() {
        finish();
    }
}
