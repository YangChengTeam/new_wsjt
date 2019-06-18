package com.yc.wsjt.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.jaeger.library.StatusBarUtil;
import com.orhanobut.logger.Logger;
import com.yc.wsjt.App;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.EmojiMessage;
import com.yc.wsjt.bean.MessageContent;
import com.yc.wsjt.bean.WeixinChatInfo;
import com.yc.wsjt.common.Constants;
import com.yc.wsjt.presenter.Presenter;

import butterknife.BindView;
import butterknife.OnClick;

public class ChatEmojiActivity extends BaseActivity {

    public static final int REQUEST_CODE_EMOJI = 1;

    @BindView(R.id.btn_config)
    Button mConfigBtn;

    @BindView(R.id.layout_send_info)
    RelativeLayout mSendInfoLayout;

    @BindView(R.id.tv_send_user_name)
    TextView mSendUserNameTv;

    @BindView(R.id.iv_send_user_head)
    ImageView mSendUserHeadIv;

    @BindView(R.id.tv_choose_hint)
    TextView mChooseHintTv;

    @BindView(R.id.iv_choose_emoji)
    ImageView mChooseEmojiIv;

    private int emojiUrl = -1;

    boolean isMySelf = true;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_chat_emoji;
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
    }

    @OnClick(R.id.layout_send_info)
    void changeSendRole() {
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

    @OnClick(R.id.layout_choose_emoji)
    void chooseEmoji() {
        Intent intent = new Intent(this, EmojiListActivity.class);
        startActivityForResult(intent, REQUEST_CODE_EMOJI);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Logger.i("onActivityResult--->" + requestCode);

        if (data != null && data.getExtras() != null) {
            emojiUrl = data.getExtras().getInt("emoji_url");
            Glide.with(this).load(emojiUrl).into(mChooseEmojiIv);
        }
    }

    @OnClick(R.id.btn_config)
    void config() {
        if (emojiUrl < 0) {
            ToastUtils.showLong("请选择表情");
            return;
        }

        int type = MessageContent.SEND_EMOJI;
        if (!SPUtils.getInstance().getBoolean(Constants.IS_SELF, true)) {
            type = MessageContent.RECEIVE_EMOJI;
        }

        //插入到外层的列表中
        WeixinChatInfo weixinChatInfo = new WeixinChatInfo();
        weixinChatInfo.setWxMainId(App.getApp().getMessageContent().getWxMainId());
        weixinChatInfo.setTypeIcon(R.mipmap.type_emoji);
        weixinChatInfo.setType(type);
        mAppDatabase.weixinChatInfoDao().insert(weixinChatInfo);

        //插入一条时间设置记录
        EmojiMessage emojiMessage = new EmojiMessage();
        emojiMessage.setWxMainId(App.getApp().getMessageContent().getWxMainId());
        emojiMessage.setMessageUserName(isMySelf ? App.getApp().chatDataInfo.getPersonName() : App.getApp().chatDataInfo.getOtherPersonName());
        emojiMessage.setMessageUserHead(isMySelf ? App.getApp().chatDataInfo.getPersonHead() : App.getApp().chatDataInfo.getOtherPersonHead());
        emojiMessage.setMessageType(type);
        emojiMessage.setEmojiUrl(emojiUrl);
        mAppDatabase.emojiMessageDao().insert(emojiMessage);

        finish();
    }
}
