package com.yc.wsjt.ui.activity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.jaeger.library.StatusBarUtil;
import com.kyleduo.switchbutton.SwitchButton;
import com.yc.wsjt.App;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.AudioMessage;
import com.yc.wsjt.bean.MessageContent;
import com.yc.wsjt.bean.WeixinChatInfo;
import com.yc.wsjt.bean.WeixinQunChatInfo;
import com.yc.wsjt.common.Constants;
import com.yc.wsjt.presenter.Presenter;
import com.yc.wsjt.ui.custom.RoleSelectDialog;

import butterknife.BindView;
import butterknife.OnClick;

public class ChatAudioActivity extends BaseActivity implements RoleSelectDialog.ChooseRoleListener {

    @BindView(R.id.tv_title)
    TextView mTitleTv;

    @BindView(R.id.btn_config)
    Button mConfigBtn;

    @BindView(R.id.layout_send_info)
    RelativeLayout mSendInfoLayout;

    @BindView(R.id.tv_send_user_name)
    TextView mSendUserNameTv;

    @BindView(R.id.iv_send_user_head)
    ImageView mSendUserHeadIv;

    @BindView(R.id.sb_audio_time)
    SeekBar mSeekBarAudioSb;

    @BindView(R.id.tv_audio_second)
    TextView mAudioLengthTv;

    @BindView(R.id.sb_is_read)
    SwitchButton mIsReadSButton;

    @BindView(R.id.sb_turn_message)
    SwitchButton mTurnSButton;

    private int audioTime;

    boolean isMySelf = true;

    private boolean isQunLiao;

    private int CHAT_TYPE = MessageContent.SEND_VOICE;

    private String sendUserName;

    private String sendUserHead;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_chat_audio;
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
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            isQunLiao = bundle.getBoolean("is_qunliao", false);
        }
        mTitleTv.setText(isQunLiao ? "群聊语音" : "单聊语音");

        if (App.getApp().chatDataInfo != null) {
            boolean isMySelf = SPUtils.getInstance().getBoolean(Constants.IS_SELF, true);
            mSendUserNameTv.setText(isMySelf ? App.getApp().chatDataInfo.getPersonName() : App.getApp().chatDataInfo.getOtherPersonName());

            if (isMySelf) {
                if (StringUtils.isEmpty(App.getApp().chatDataInfo.getPersonHead())) {
                    Glide.with(this).load(R.mipmap.user_head_def).into(mSendUserHeadIv);
                } else {
                    Glide.with(this).load(App.getApp().chatDataInfo.getPersonHead()).into(mSendUserHeadIv);
                }
            } else {
                if (StringUtils.isEmpty(App.getApp().chatDataInfo.getOtherPersonHead())) {
                    Glide.with(this).load(R.mipmap.user_head_def).into(mSendUserHeadIv);
                } else {
                    Glide.with(this).load(App.getApp().chatDataInfo.getOtherPersonHead()).into(mSendUserHeadIv);
                }
            }
        }

        mSeekBarAudioSb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioTime = progress;
                mAudioLengthTv.setText(progress + " 秒");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    @OnClick(R.id.layout_send_info)
    void changeSendRole() {
        if (isQunLiao) {
            RoleSelectDialog roleSelectDialog = new RoleSelectDialog(this, R.style.custom_dialog);
            roleSelectDialog.setChooseRoleListener(this);
            roleSelectDialog.show();

            roleSelectDialog.setCanceledOnTouchOutside(true);
            WindowManager.LayoutParams windowParams = roleSelectDialog.getWindow().getAttributes();
            windowParams.width = (int) (ScreenUtils.getScreenWidth() * 0.92);
            windowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
            roleSelectDialog.getWindow().setAttributes(windowParams);
        } else {
            if (App.getApp().chatDataInfo != null) {
                isMySelf = SPUtils.getInstance().getBoolean(Constants.IS_SELF, true);
                isMySelf = !isMySelf;
                SPUtils.getInstance().put(Constants.IS_SELF, isMySelf);
                mSendUserNameTv.setText(isMySelf ? App.getApp().chatDataInfo.getPersonName() : App.getApp().chatDataInfo.getOtherPersonName());
                if (isMySelf) {
                    if (StringUtils.isEmpty(App.getApp().chatDataInfo.getPersonHead())) {
                        Glide.with(this).load(R.mipmap.user_head_def).into(mSendUserHeadIv);
                    } else {
                        Glide.with(this).load(App.getApp().chatDataInfo.getPersonHead()).into(mSendUserHeadIv);
                    }
                } else {
                    if (StringUtils.isEmpty(App.getApp().chatDataInfo.getOtherPersonHead())) {
                        Glide.with(this).load(R.mipmap.user_head_def).into(mSendUserHeadIv);
                    } else {
                        Glide.with(this).load(App.getApp().chatDataInfo.getOtherPersonHead()).into(mSendUserHeadIv);
                    }
                }
            }
        }
    }


    @OnClick(R.id.btn_config)
    void config() {
        if (isQunLiao) {
            //插入一条时间设置记录
            AudioMessage audioMessage = new AudioMessage();
            //audioMessage.setWxMainId(App.getApp().getMessageContent().getWxMainId());
            audioMessage.setMessageType(CHAT_TYPE);
            audioMessage.setMessageUserName(sendUserName);
            audioMessage.setMessageUserHead(sendUserHead);
            audioMessage.setAudioTime(audioTime);
            audioMessage.setRead(mIsReadSButton.isChecked());
            audioMessage.setOpenAudioTurn(mTurnSButton.isChecked());
            Long audioId = mAppDatabase.audioMessageDao().insert(audioMessage);

            //插入到外层的列表中
            WeixinQunChatInfo weixinQunChatInfo = new WeixinQunChatInfo();
            weixinQunChatInfo.setMainId(App.getApp().getMessageContent().getId());
            weixinQunChatInfo.setTypeIcon(R.mipmap.type_voice);
            weixinQunChatInfo.setType(CHAT_TYPE);
            weixinQunChatInfo.setChildTabId(audioId);
            mAppDatabase.weixinQunChatInfoDao().insert(weixinQunChatInfo);
        } else {
            CHAT_TYPE = MessageContent.SEND_VOICE;
            if (!SPUtils.getInstance().getBoolean(Constants.IS_SELF, true)) {
                CHAT_TYPE = MessageContent.RECEIVE_VOICE;
            }
            //插入一条时间设置记录
            AudioMessage audioMessage = new AudioMessage();
            //audioMessage.setWxMainId(App.getApp().getMessageContent().getWxMainId());
            audioMessage.setMessageType(CHAT_TYPE);
            audioMessage.setMessageUserName(isMySelf ? App.getApp().chatDataInfo.getPersonName() : App.getApp().chatDataInfo.getOtherPersonName());
            audioMessage.setMessageUserHead(isMySelf ? App.getApp().chatDataInfo.getPersonHead() : App.getApp().chatDataInfo.getOtherPersonHead());
            audioMessage.setAudioTime(audioTime);
            audioMessage.setRead(mIsReadSButton.isChecked());
            audioMessage.setOpenAudioTurn(mTurnSButton.isChecked());
            Long audioId = mAppDatabase.audioMessageDao().insert(audioMessage);

            WeixinChatInfo weixinChatInfo = new WeixinChatInfo();
            weixinChatInfo.setMainId(App.getApp().getMessageContent().getId());
            weixinChatInfo.setTypeIcon(R.mipmap.type_voice);
            weixinChatInfo.setType(CHAT_TYPE);
            weixinChatInfo.setChildTabId(audioId);
            mAppDatabase.weixinChatInfoDao().insert(weixinChatInfo);
        }

        finish();
    }

    @OnClick(R.id.iv_back)
    void back() {
        finish();
    }

    @Override
    public void chooseRole(int pos, String name, String head, int localHead) {
        if (pos > 0) {
            CHAT_TYPE = MessageContent.RECEIVE_VOICE;
        } else {
            CHAT_TYPE = MessageContent.SEND_VOICE;
        }
        mSendUserNameTv.setText(name);
        sendUserName = name;
        Glide.with(this).load(head).into(mSendUserHeadIv);
        sendUserHead = head;
    }
}
