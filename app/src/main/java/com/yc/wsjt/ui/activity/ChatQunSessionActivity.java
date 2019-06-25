package com.yc.wsjt.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.jaeger.library.StatusBarUtil;
import com.lqr.adapter.LQRViewHolder;
import com.lqr.adapter.OnItemClickListener;
import com.lqr.recyclerview.LQRRecyclerView;
import com.yc.wsjt.App;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.AudioMessage;
import com.yc.wsjt.bean.EmojiMessage;
import com.yc.wsjt.bean.ImageMessage;
import com.yc.wsjt.bean.MessageContent;
import com.yc.wsjt.bean.PersonMessage;
import com.yc.wsjt.bean.RedPackageMessage;
import com.yc.wsjt.bean.ShareMessage;
import com.yc.wsjt.bean.SystemTipsMessage;
import com.yc.wsjt.bean.TextMessage;
import com.yc.wsjt.bean.TimeMessage;
import com.yc.wsjt.bean.WeixinQunChatInfo;
import com.yc.wsjt.presenter.Presenter;
import com.yc.wsjt.ui.adapter.QunSessionAdapter;
import com.yc.wsjt.ui.custom.QianghongbaoDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by zhangdinghui on 2019/5/15.
 */
public class ChatQunSessionActivity extends BaseActivity {

    @BindView(R.id.chat_session_list)
    LQRRecyclerView mChatSessionListView;

    @BindView(R.id.tv_title)
    TextView mTitleTv;

    QunSessionAdapter sessionAdapter;

    List<MessageContent> list = new ArrayList<>();

    QianghongbaoDialog qianghongbaoDialog;

    WindowManager.LayoutParams windowParams;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_qun_session;
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
        if (App.getApp().qunChatInfo != null) {
            mTitleTv.setText(App.getApp().qunChatInfo.getQunLiaoName() + "(" + App.getApp().qunChatInfo.getChatPersonNumber() + ")");
        }
    }

    @Override
    protected void initViews() {
        qianghongbaoDialog = new QianghongbaoDialog(this, R.style.custom_dialog);
        qianghongbaoDialog.setCanceledOnTouchOutside(true);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        try {

            if (App.getApp().getQunChatList() != null && App.getApp().getQunChatList().size() > 0) {
                for (WeixinQunChatInfo weixinQunChatInfo : App.getApp().getQunChatList()) {
                    switch (weixinQunChatInfo.getType()) {
                        case MessageContent.CHAT_DATE:
                            TimeMessage timeMessage = mAppDatabase.timeMessageDao().getItemById(weixinQunChatInfo.getChildTabId());
                            list.add(timeMessage);
                            break;
                        case MessageContent.SEND_TEXT:
                        case MessageContent.RECEIVE_TEXT:
                            TextMessage textMessage = mAppDatabase.textMessageDao().getItemById(weixinQunChatInfo.getChildTabId());
                            list.add(textMessage);
                            break;
                        case MessageContent.SEND_IMAGE:
                        case MessageContent.RECEIVE_IMAGE:
                            ImageMessage imageMessage = mAppDatabase.imageMessageDao().getItemById(weixinQunChatInfo.getChildTabId());
                            list.add(imageMessage);
                            break;
                        case MessageContent.SEND_VOICE:
                        case MessageContent.RECEIVE_VOICE:
                            AudioMessage audioMessage = mAppDatabase.audioMessageDao().getItemById(weixinQunChatInfo.getChildTabId());
                            list.add(audioMessage);
                            break;
                        case MessageContent.SEND_EMOJI:
                        case MessageContent.RECEIVE_EMOJI:
                            EmojiMessage emojiMessage = mAppDatabase.emojiMessageDao().getItemById(weixinQunChatInfo.getChildTabId());
                            list.add(emojiMessage);
                            break;
                        case MessageContent.SEND_RED_PACKET:
                        case MessageContent.RECEIVE_RED_PACKET:
                            RedPackageMessage redPackageMessage = mAppDatabase.redMessageDao().getItemById(weixinQunChatInfo.getChildTabId());
                            list.add(redPackageMessage);
                            break;
                        //TODO 此处待完善
//                        case MessageContent.QUN_RECEIVE_RED:
//                            RedPackageMessage redPackageMessage1 = mAppDatabase.redMessageDao().getItemById(weixinQunChatInfo.getChildTabId());
//                            list.add(redPackageMessage1);
//                            break;
                        case MessageContent.SEND_SHARE:
                        case MessageContent.RECEIVE_SHARE:
                            ShareMessage shareMessage = mAppDatabase.shareMessageDao().getItemById(weixinQunChatInfo.getChildTabId());
                            list.add(shareMessage);
                            break;
                        case MessageContent.SEND_PERSON_CARD:
                        case MessageContent.RECEIVE_PERSON_CARD:
                            PersonMessage personMessage = mAppDatabase.personMessageDao().getItemById(weixinQunChatInfo.getChildTabId());
                            list.add(personMessage);
                            break;
                        case MessageContent.SYSTEM_TIPS:
                            SystemTipsMessage systemTipsMessage = mAppDatabase.systemTipsMessageDao().getItemById(weixinQunChatInfo.getChildTabId());
                            list.add(systemTipsMessage);
                            break;
                    }
                }
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }

        sessionAdapter = new QunSessionAdapter(this, list);
        mChatSessionListView.setAdapter(sessionAdapter);

        sessionAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(LQRViewHolder helper, ViewGroup parent, View itemView, int position) {
                if (sessionAdapter.getData().get(position).getMessageType() == MessageContent.SEND_RED_PACKET || sessionAdapter.getData().get(position).getMessageType() == MessageContent.RECEIVE_RED_PACKET) {
                    RedPackageMessage redPackageMessage = (RedPackageMessage) sessionAdapter.getData().get(position);

                    Long redId = Long.valueOf(redPackageMessage.getId());
                    if (mAppDatabase.redRecordInfoDao().getListByRedId(redId) != null && mAppDatabase.redRecordInfoDao().getListByRedId(redId).size() > 0) {
                        Intent intent = new Intent(ChatQunSessionActivity.this, QunHongBaoActivity.class);
                        intent.putExtra("redId", redPackageMessage.getId());
                        intent.putExtra("send_user_name", redPackageMessage.getMessageUserName());
                        intent.putExtra("send_user_head", redPackageMessage.getMessageUserHead());
                        intent.putExtra("red_remark", redPackageMessage.getRedDesc());
                        intent.putExtra("red_money", Float.parseFloat(redPackageMessage.getRedNumber()));
                        intent.putExtra("red_count", redPackageMessage.getRedCount());
                        startActivity(intent);
                    } else {
                        if (qianghongbaoDialog != null && !qianghongbaoDialog.isShowing()) {
                            qianghongbaoDialog.show();
                            qianghongbaoDialog.setRedPackageMessage(redPackageMessage);
                        }
                    }
                }
            }
        });
    }

    @OnClick(R.id.iv_back)
    void back() {
        finish();
    }

}
