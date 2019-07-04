package com.yc.wsjt.ui.activity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.PhoneUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.bumptech.glide.Glide;
import com.jaeger.library.StatusBarUtil;
import com.lqr.adapter.LQRViewHolder;
import com.lqr.adapter.OnItemClickListener;
import com.lqr.recyclerview.LQRRecyclerView;
import com.yc.wsjt.App;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.AudioMessage;
import com.yc.wsjt.bean.ChatDataInfo;
import com.yc.wsjt.bean.EmojiMessage;
import com.yc.wsjt.bean.GroupMessage;
import com.yc.wsjt.bean.ImageMessage;
import com.yc.wsjt.bean.MessageContent;
import com.yc.wsjt.bean.PersonMessage;
import com.yc.wsjt.bean.RedPackageMessage;
import com.yc.wsjt.bean.ShareMessage;
import com.yc.wsjt.bean.SystemTipsMessage;
import com.yc.wsjt.bean.TextMessage;
import com.yc.wsjt.bean.TimeMessage;
import com.yc.wsjt.bean.TransferMessage;
import com.yc.wsjt.bean.VideoMessage;
import com.yc.wsjt.bean.WeixinChatInfo;
import com.yc.wsjt.presenter.Presenter;
import com.yc.wsjt.ui.adapter.SessionAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;


/**
 * Created by zhangdinghui on 2019/5/15.
 */
public class ChatSessionActivity extends BaseActivity {

    @BindView(R.id.chat_session_list)
    LQRRecyclerView mChatSessionListView;

    @BindView(R.id.iv_voice)
    ImageView mVoiceIv;

    @BindView(R.id.tv_title)
    TextView mTitleTv;

    @BindView(R.id.tv_chat_touch)
    EditText mTouchEt;

    @BindView(R.id.iv_handset_new)
    ImageView mHandSetNewIv;

    @BindView(R.id.iv_mute)
    ImageView mMuteIv;

    @BindView(R.id.iv_chat_bg)
    ImageView mChatBgIv;

    SessionAdapter sessionAdapter;

    List<MessageContent> list = new ArrayList<>();

    Disposable disposable = null;

    private boolean isPan;

    private int modelType;

    ChatDataInfo mChatDataInfo;

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
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            modelType = bundle.getInt("model_type", 0);
        }

        try {
            if (mAppDatabase.chatDataInfoDao().getItemById(PhoneUtils.getDeviceId(), modelType) != null) {
                mChatDataInfo = mAppDatabase.chatDataInfoDao().getItemById(PhoneUtils.getDeviceId(), modelType);
            }

            if (mChatDataInfo != null) {
                mHandSetNewIv.setVisibility(mChatDataInfo.messageDisturb ? View.VISIBLE : View.GONE);
                mMuteIv.setVisibility(mChatDataInfo.receiverOpen ? View.VISIBLE : View.GONE);
                mTitleTv.setText(mChatDataInfo.getPersonName());
                Glide.with(this).load(mChatDataInfo.getChatBgImage()).into(mChatBgIv);
            } else {
                Glide.with(this).load("").into(mChatBgIv);
            }

            if (App.getApp().getChatList() != null && App.getApp().getChatList().size() > 0) {
                for (WeixinChatInfo weixinChatInfo : App.getApp().getChatList()) {
                    switch (weixinChatInfo.getType()) {
                        case MessageContent.CHAT_DATE:
                            TimeMessage timeMessage = mAppDatabase.timeMessageDao().getItemById(weixinChatInfo.getChildTabId());
                            list.add(timeMessage);
                            break;
                        case MessageContent.SEND_TEXT:
                        case MessageContent.RECEIVE_TEXT:
                            TextMessage textMessage = mAppDatabase.textMessageDao().getItemById(weixinChatInfo.getChildTabId());
                            list.add(textMessage);
                            break;
                        case MessageContent.SEND_IMAGE:
                        case MessageContent.RECEIVE_IMAGE:
                            ImageMessage imageMessage = mAppDatabase.imageMessageDao().getItemById(weixinChatInfo.getChildTabId());
                            list.add(imageMessage);
                            break;
                        case MessageContent.SEND_VOICE:
                        case MessageContent.RECEIVE_VOICE:
                            AudioMessage audioMessage = mAppDatabase.audioMessageDao().getItemById(weixinChatInfo.getChildTabId());
                            list.add(audioMessage);
                            break;
                        case MessageContent.SEND_EMOJI:
                        case MessageContent.RECEIVE_EMOJI:
                            EmojiMessage emojiMessage = mAppDatabase.emojiMessageDao().getItemById(weixinChatInfo.getChildTabId());
                            list.add(emojiMessage);
                            break;
                        case MessageContent.SEND_RED_PACKET:
                        case MessageContent.RECEIVE_RED_PACKET:
                        case MessageContent.ROB_RED_PACKET:
                            RedPackageMessage redPackageMessage = mAppDatabase.redMessageDao().getItemById(weixinChatInfo.getChildTabId());
                            list.add(redPackageMessage);
                            break;
                        case MessageContent.SEND_TRANSFER:
                        case MessageContent.RECEIVE_TRANSFER:
                            TransferMessage transferMessage = mAppDatabase.transferMessageDao().getItemById(weixinChatInfo.getChildTabId());
                            list.add(transferMessage);
                            break;
                        case MessageContent.SEND_VIDEO:
                        case MessageContent.RECEIVE_VIDEO:
                            VideoMessage videoMessage = mAppDatabase.videoMessageDao().getItemById(weixinChatInfo.getChildTabId());
                            list.add(videoMessage);
                            break;
                        case MessageContent.SEND_SHARE:
                        case MessageContent.RECEIVE_SHARE:
                            ShareMessage shareMessage = mAppDatabase.shareMessageDao().getItemById(weixinChatInfo.getChildTabId());
                            list.add(shareMessage);
                            break;
                        case MessageContent.SEND_PERSON_CARD:
                        case MessageContent.RECEIVE_PERSON_CARD:
                            PersonMessage personMessage = mAppDatabase.personMessageDao().getItemById(weixinChatInfo.getChildTabId());
                            list.add(personMessage);
                            break;
                        case MessageContent.SEND_JOIN_GROUP:
                        case MessageContent.RECEIVE_JOIN_GROUP:
                            GroupMessage groupMessage = mAppDatabase.groupMessageDao().getItemById(weixinChatInfo.getChildTabId());
                            list.add(groupMessage);
                            break;
                        case MessageContent.SYSTEM_TIPS:
                            SystemTipsMessage systemTipsMessage = mAppDatabase.systemTipsMessageDao().getItemById(weixinChatInfo.getChildTabId());
                            list.add(systemTipsMessage);
                            break;
                    }
                }
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }

        sessionAdapter = new SessionAdapter(this, list);
        mChatSessionListView.setAdapter(sessionAdapter);

        sessionAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(LQRViewHolder helper, ViewGroup parent, View itemView, int position) {
                if (sessionAdapter.getData().get(position) instanceof TransferMessage) {
                    if (!((TransferMessage) list.get(position)).isReceive()) {
                        mAppDatabase.transferMessageDao().updateTransReceive(!((TransferMessage)sessionAdapter.getData().get(position)).isReceive(), sessionAdapter.getData().get(position).getId());
                        ((TransferMessage)sessionAdapter.getData().get(position)).setReceive(true);
                        sessionAdapter.notifyItemChanged(position);
                    }
                }
            }
        });

    }

    @OnClick(R.id.iv_voice)
    void voiceClick() {
        isPan = !isPan;
        if (isPan) {
            Glide.with(this).load(R.mipmap.chat_pan_icon).into(mVoiceIv);
            mTouchEt.setText("按住 说话");
            mTouchEt.setFocusable(false);
            mTouchEt.setFocusableInTouchMode(false);
            mTouchEt.setGravity(Gravity.CENTER);
            mTouchEt.setPadding(0, 0, 0, 0);
        } else {
            Glide.with(this).load(R.mipmap.chat_voice_icon).into(mVoiceIv);
            mTouchEt.setText("");
            mTouchEt.setFocusable(true);
            mTouchEt.setFocusableInTouchMode(true);
            mTouchEt.setGravity(Gravity.LEFT | Gravity.CENTER);
            mTouchEt.setPadding(SizeUtils.dp2px(8), 0, 0, 0);
        }
    }

    @OnClick(R.id.iv_back)
    void back() {
        finish();
    }

}
