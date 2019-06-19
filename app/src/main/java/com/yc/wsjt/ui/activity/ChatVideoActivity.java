package com.yc.wsjt.ui.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.jaeger.library.StatusBarUtil;
import com.yc.wsjt.App;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.MessageContent;
import com.yc.wsjt.bean.VideoMessage;
import com.yc.wsjt.bean.WeixinChatInfo;
import com.yc.wsjt.common.Constants;
import com.yc.wsjt.presenter.Presenter;
import com.yc.wsjt.ui.custom.VideoTimeDialog;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

public class ChatVideoActivity extends BaseActivity implements VideoTimeDialog.DateSelectListener, View.OnClickListener {

    private static final int REQUEST_CODE_CHOOSE = 1000;

    @BindView(R.id.btn_config)
    Button mConfigBtn;

    @BindView(R.id.layout_send_info)
    RelativeLayout mSendInfoLayout;

    @BindView(R.id.tv_send_user_name)
    TextView mSendUserNameTv;

    @BindView(R.id.iv_send_user_head)
    ImageView mSendUserHeadIv;

    @BindView(R.id.tv_connection_state)
    TextView mConnectionStateTv;

    @BindView(R.id.layout_state)
    RelativeLayout mStateLayout;

    @BindView(R.id.tv_video_type)
    TextView mVideoTypeTv;

    @BindView(R.id.tv_voice_type)
    TextView mVoiceTypeTv;

    @BindView(R.id.layout_video_time)
    LinearLayout mVideoLayout;

    @BindView(R.id.tv_video_time)
    TextView mVideoTimeTv;

    VideoTimeDialog videoTimeDialog;

    private File outputImage;

    private int chooseType = 1;//视频(1),语音

    BottomSheetDialog bottomSheetDialog;

    LinearLayout mConnectLayout;

    LinearLayout mConnectCancelLayout;

    LinearLayout mRefuseLayout;

    LinearLayout mCancelLayout;

    private int chatState = 1;

    boolean isMySelf = true;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_chat_video;
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
        videoTimeDialog = new VideoTimeDialog(this, R.style.video_time_dialog, 10);
        videoTimeDialog.setDateSelectListener(this);

        bottomSheetDialog = new BottomSheetDialog(this);
        View connectView = LayoutInflater.from(this).inflate(R.layout.video_connection_view, null);
        mConnectLayout = connectView.findViewById(R.id.layout_connect);
        mConnectCancelLayout = connectView.findViewById(R.id.layout_connect_cancel);
        mRefuseLayout = connectView.findViewById(R.id.layout_refuse);
        mCancelLayout = connectView.findViewById(R.id.layout_view_cancel);

        mConnectLayout.setOnClickListener(this);
        mConnectCancelLayout.setOnClickListener(this);
        mRefuseLayout.setOnClickListener(this);
        mCancelLayout.setOnClickListener(this);

        bottomSheetDialog.setContentView(connectView);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        if (App.getApp().chatDataInfo != null) {
            isMySelf = SPUtils.getInstance().getBoolean(Constants.IS_SELF, true);
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

    @OnClick(R.id.layout_state)
    void connectionState() {
        if (bottomSheetDialog != null && !bottomSheetDialog.isShowing()) {
            bottomSheetDialog.show();
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

    @OnClick(R.id.tv_video_type)
    void imageType() {
        chooseType = 1;
        mVideoTypeTv.setBackgroundResource(R.drawable.choose_type_selected);
        mVideoTypeTv.setTextColor(ContextCompat.getColor(this, R.color.white));

        mVoiceTypeTv.setBackgroundResource(R.drawable.choose_type_normal);
        mVoiceTypeTv.setTextColor(ContextCompat.getColor(this, R.color.black));
    }

    @OnClick(R.id.tv_voice_type)
    void videoType() {
        chooseType = 2;

        mVideoTypeTv.setBackgroundResource(R.drawable.choose_type_normal);
        mVideoTypeTv.setTextColor(ContextCompat.getColor(this, R.color.black));

        mVoiceTypeTv.setBackgroundResource(R.drawable.choose_type_selected);
        mVoiceTypeTv.setTextColor(ContextCompat.getColor(this, R.color.white));
    }

    @OnClick(R.id.layout_video_time)
    void setVideoTime() {
        videoTimeDialog.show();

        WindowManager.LayoutParams windowParams = videoTimeDialog.getWindow().getAttributes();
        windowParams.width = (int) (ScreenUtils.getScreenWidth() * 0.7);
        windowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        videoTimeDialog.getWindow().setAttributes(windowParams);
    }

    @Override
    public void configDate(String selectDate) {
        if (!StringUtils.isEmpty(selectDate)) {
            mVideoTimeTv.setText(selectDate);
        }
    }

    @Override
    public void onClick(View v) {
        if (bottomSheetDialog != null && bottomSheetDialog.isShowing()) {
            bottomSheetDialog.dismiss();
        }

        switch (v.getId()) {
            case R.id.layout_connect:
                chatState = 1;
                mConnectionStateTv.setText("已接通");
                break;
            case R.id.layout_connect_cancel:
                chatState = 2;
                mConnectionStateTv.setText("已取消");
                break;
            case R.id.layout_refuse:
                chatState = 3;
                mConnectionStateTv.setText("已拒绝");
                break;
            case R.id.layout_view_cancel:
                chatState = 1;
                break;
            default:
                chatState = 1;
                break;
        }
    }

    @OnClick(R.id.btn_config)
    void config() {
        //Logger.i("chat text--->" + mChatEditText.getText().toString());
        int type = MessageContent.SEND_VIDEO;
        if (!SPUtils.getInstance().getBoolean(Constants.IS_SELF, true)) {
            type = MessageContent.RECEIVE_VIDEO;
        }

        //插入一条时间设置记录
        VideoMessage videoMessage = new VideoMessage();
        videoMessage.setWxMainId(App.getApp().getMessageContent().getWxMainId());
        videoMessage.setMessageType(type);
        videoMessage.setMessageContent(mConnectionStateTv.getText() + " " + mVideoTimeTv.getText().toString());
        videoMessage.setChatVideoType(chooseType);
        videoMessage.setChatTime(mVideoTimeTv.getText().toString());
        videoMessage.setChatState(chatState);
        videoMessage.setMessageUserName(isMySelf ? App.getApp().chatDataInfo.getPersonName() : App.getApp().chatDataInfo.getOtherPersonName());
        videoMessage.setMessageUserHead(isMySelf ? App.getApp().chatDataInfo.getPersonHead() : App.getApp().chatDataInfo.getOtherPersonHead());
        videoMessage.setChatImageUrl(chooseType == 1 ? R.mipmap.chat_item_video : R.mipmap.chat_item_voice);
        Long videoId = mAppDatabase.videoMessageDao().insert(videoMessage);

        //插入到外层的列表中
        WeixinChatInfo weixinChatInfo = new WeixinChatInfo();
        weixinChatInfo.setWxMainId(App.getApp().getMessageContent().getWxMainId());
        weixinChatInfo.setTypeIcon(chooseType == 1 ? R.mipmap.type_video : R.mipmap.type_voice);
        weixinChatInfo.setChatText(mConnectionStateTv.getText() + " " + mVideoTimeTv.getText());
        weixinChatInfo.setType(type);
        weixinChatInfo.setChildTabId(videoId);

        mAppDatabase.weixinChatInfoDao().insert(weixinChatInfo);

        finish();
    }
}
