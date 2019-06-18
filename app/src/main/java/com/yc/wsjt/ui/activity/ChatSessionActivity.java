package com.yc.wsjt.ui.activity;

import android.os.Bundle;

import androidx.core.content.ContextCompat;

import com.jaeger.library.StatusBarUtil;
import com.lqr.recyclerview.LQRRecyclerView;
import com.yc.wsjt.App;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.MessageContent;
import com.yc.wsjt.presenter.Presenter;
import com.yc.wsjt.ui.adapter.SessionAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by zhangdinghui on 2019/5/15.
 */
public class ChatSessionActivity extends BaseActivity {

    @BindView(R.id.chat_session_list)
    LQRRecyclerView mChatSessionListView;

    SessionAdapter sessionAdapter;

    List<MessageContent> list = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_session;
    }

    @Override
    protected Presenter getPresenter() {
        return null;
    }

    @Override
    public void setStatusBar() {
        super.setStatusBar();
        StatusBarUtil.setLightMode(this);
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.chat_bg_color), 0);
    }

    @Override
    protected void initVars() {

    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        if(App.getApp().getMessageContent() != null){
            int wxMainId = App.getApp().getMessageContent().getWxMainId();
            list.addAll(mAppDatabase.timeMessageDao().getItemById(wxMainId));
            list.addAll(mAppDatabase.textMessageDao().getItemById(wxMainId));
            list.addAll(mAppDatabase.imageMessageDao().getItemById(wxMainId));
            list.addAll(mAppDatabase.audioMessageDao().getItemById(wxMainId));
            list.addAll(mAppDatabase.emojiMessageDao().getItemById(wxMainId));
            list.addAll(mAppDatabase.redMessageDao().getItemById(wxMainId));
            list.addAll(mAppDatabase.transferMessageDao().getItemById(wxMainId));
            list.addAll(mAppDatabase.videoMessageDao().getItemById(wxMainId));
            list.addAll(mAppDatabase.shareMessageDao().getItemById(wxMainId));
            list.addAll(mAppDatabase.personMessageDao().getItemById(wxMainId));
            list.addAll(mAppDatabase.groupMessageDao().getItemById(wxMainId));
            list.addAll(mAppDatabase.systemTipsMessageDao().getItemById(wxMainId));
        }

        sessionAdapter = new SessionAdapter(this, list);
        mChatSessionListView.setAdapter(sessionAdapter);
    }

    @OnClick(R.id.iv_back)
    void back() {
        finish();
    }

}
