package com.yc.wsjt.ui.activity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jaeger.library.StatusBarUtil;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.orhanobut.logger.Logger;
import com.yc.wsjt.App;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.MessageContent;
import com.yc.wsjt.bean.TimeMessage;
import com.yc.wsjt.bean.WeixinChatInfo;
import com.yc.wsjt.bean.WeixinQunChatInfo;
import com.yc.wsjt.presenter.Presenter;
import com.yc.wsjt.ui.custom.TwelveDialog;
import com.yc.wsjt.ui.custom.TwentyFourDialog;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhangdinghui on 2019/5/8.
 */
public class ChatTimeActivity extends BaseActivity implements OnDateSetListener, TwelveDialog.DateSelectListener, TwentyFourDialog.TFDateSelectListener {

    @BindView(R.id.tv_title)
    TextView mTitleTv;

    @BindView(R.id.btn_config)
    Button mConfigBtn;

    @BindView(R.id.et_select_time)
    EditText mSelectTimeEt;

    @BindView(R.id.btn_twelve)
    Button mTwelveBtn;

    @BindView(R.id.btn_twenty_four)
    Button mTwentyFourBtn;

    TwelveDialog twelveDialog;

    TwentyFourDialog twentyFourDialog;

    private boolean isQunLiao;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_chat_time;
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
        twelveDialog = new TwelveDialog(this, R.style.date_dialog);
        twelveDialog.setDateSelectListener(this);

        twentyFourDialog = new TwentyFourDialog(this, R.style.date_dialog);
        twentyFourDialog.setTfDateSelectListener(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            isQunLiao = bundle.getBoolean("is_qunliao", false);
        }
        mTitleTv.setText(isQunLiao ? "群聊时间" : "单聊时间");
    }

    @OnClick(R.id.btn_twelve)
    void twelve() {
        twelveDialog.show();

        //设置Dialog从窗体底部弹出
        twelveDialog.getWindow().setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams windowParams = twelveDialog.getWindow().getAttributes();
        windowParams.width = ScreenUtils.getScreenWidth();
        windowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        twelveDialog.getWindow().setAttributes(windowParams);
    }

    @OnClick(R.id.btn_twenty_four)
    void twentyFour() {
        twentyFourDialog.show();

        //设置Dialog从窗体底部弹出
        twentyFourDialog.getWindow().setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams windowParams = twentyFourDialog.getWindow().getAttributes();
        windowParams.width = ScreenUtils.getScreenWidth();
        windowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        twentyFourDialog.getWindow().setAttributes(windowParams);
    }

    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
        Logger.i("setting date --->" + millseconds + "---" + TimeUtils.millis2Date(millseconds));
    }

    @Override
    public void configDate(String selectDate) {
        if (!StringUtils.isEmpty(selectDate)) {
            mSelectTimeEt.setText(selectDate);
            mSelectTimeEt.setSelection(selectDate.length());
        }
    }

    @Override
    public void tfConfigDate(String selectDate) {
        if (!StringUtils.isEmpty(selectDate)) {
            mSelectTimeEt.setText(selectDate);
            mSelectTimeEt.setSelection(selectDate.length());
        }
    }

    @OnClick(R.id.btn_config)
    void configSetting() {
        if (StringUtils.isEmpty(mSelectTimeEt.getText())) {
            ToastUtils.showLong("请填写时间");
            return;
        }

        //插入一条时间设置记录
        TimeMessage timeMessage = new TimeMessage();
        //timeMessage.setWxMainId(App.getApp().getMessageContent().getWxMainId());
        timeMessage.setMessageType(MessageContent.CHAT_DATE);
        timeMessage.setLocalMessageImg(R.mipmap.type_time);
        timeMessage.setMessageContent(mSelectTimeEt.getText().toString());
        Long timeId = mAppDatabase.timeMessageDao().insert(timeMessage);
        Logger.i("timeId--->" + timeId);

        //插入到外层的列表中
        if (isQunLiao) {
            WeixinQunChatInfo weixinQunChatInfo = new WeixinQunChatInfo();
            weixinQunChatInfo.setMainId(App.getApp().getMessageContent().getId());
            weixinQunChatInfo.setTypeIcon(R.mipmap.type_time);
            weixinQunChatInfo.setChatText(mSelectTimeEt.getText().toString());
            weixinQunChatInfo.setType(MessageContent.CHAT_DATE);
            weixinQunChatInfo.setChildTabId(timeId);
            mAppDatabase.weixinQunChatInfoDao().insert(weixinQunChatInfo);
        } else {
            WeixinChatInfo weixinChatInfo = new WeixinChatInfo();
            weixinChatInfo.setMainId(App.getApp().getMessageContent().getId());
            weixinChatInfo.setTypeIcon(R.mipmap.type_time);
            weixinChatInfo.setChatText(mSelectTimeEt.getText().toString());
            weixinChatInfo.setType(MessageContent.CHAT_DATE);
            weixinChatInfo.setChildTabId(timeId);
            mAppDatabase.weixinChatInfoDao().insert(weixinChatInfo);
        }

        finish();
    }

    @OnClick(R.id.iv_back)
    void back() {
        finish();
    }
}
