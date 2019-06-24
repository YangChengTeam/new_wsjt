package com.yc.wsjt.ui.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.jaeger.library.StatusBarUtil;
import com.orhanobut.logger.Logger;
import com.yc.wsjt.App;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.ImageMessage;
import com.yc.wsjt.bean.MessageContent;
import com.yc.wsjt.bean.WeixinChatInfo;
import com.yc.wsjt.bean.WeixinQunChatInfo;
import com.yc.wsjt.common.Constants;
import com.yc.wsjt.presenter.Presenter;
import com.yc.wsjt.ui.custom.Glide4Engine;
import com.yc.wsjt.ui.custom.RoleSelectDialog;
import com.yc.wsjt.ui.custom.VideoTimeDialog;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

public class ChatImageActivity extends BaseActivity implements VideoTimeDialog.DateSelectListener, RoleSelectDialog.ChooseRoleListener {

    private static final int REQUEST_CODE_CHOOSE = 1000;

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

    @BindView(R.id.tv_choose_hint)
    TextView mChooseHintTv;

    @BindView(R.id.layout_choose_img)
    RelativeLayout mChooseImgLayout;

    @BindView(R.id.iv_choose_img)
    ImageView mChooseIv;

    @BindView(R.id.tv_image_type)
    TextView mImageTypeTv;

    @BindView(R.id.tv_video_type)
    TextView mVideoTypeTv;

    @BindView(R.id.tv_video_time)
    TextView mVideoTimeTv;

    @BindView(R.id.layout_video_time)
    LinearLayout mVideoLayout;

    VideoTimeDialog videoTimeDialog;

    private int chooseType = 1;//图片(1)，视频

    private String imagePath;

    private String videoPath;

    boolean isMySelf = true;

    private boolean isQunLiao;

    private String sendUserName;

    private String sendUserHead;

    private int CHAT_TYPE = MessageContent.SEND_IMAGE;

    private int chooseRolePos;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_chat_image;
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
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            isQunLiao = bundle.getBoolean("is_qunliao", false);
        }
        mTitleTv.setText(isQunLiao ? "群聊图片" : "单聊图片");

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

    @OnClick(R.id.layout_choose_img)
    void chooseImage() {
        Matisse.from(this)
                .choose(chooseType == 1 ? MimeType.ofImage() : MimeType.ofVideo())
                .countable(true)
                .maxSelectable(1)
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(new Glide4Engine())
                .showSingleMediaType(true)
                .forResult(REQUEST_CODE_CHOOSE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_CHOOSE:
                    Logger.i(JSONObject.toJSONString(Matisse.obtainPathResult(data)));
                    if (Matisse.obtainPathResult(data) != null && Matisse.obtainPathResult(data).size() > 0) {
                        if (chooseType == 1) {
                            imagePath = Matisse.obtainPathResult(data).get(0);
                            Glide.with(ChatImageActivity.this).load(Matisse.obtainPathResult(data).get(0)).into(mChooseIv);
                        } else {
                            videoPath = Matisse.obtainPathResult(data).get(0);
                            Logger.i("out path--->" + videoPath);
                            Glide.with(context).load(Uri.fromFile(new File(videoPath))).into(mChooseIv);
                        }
                    }
                    break;
            }
        }
    }

    @OnClick(R.id.tv_image_type)
    void imageType() {
        chooseType = 1;
        mChooseHintTv.setText("选择图片");
        mImageTypeTv.setBackgroundResource(R.drawable.choose_type_selected);
        mImageTypeTv.setTextColor(ContextCompat.getColor(this, R.color.white));

        mVideoTypeTv.setBackgroundResource(R.drawable.choose_type_normal);
        mVideoTypeTv.setTextColor(ContextCompat.getColor(this, R.color.black));
        mVideoLayout.setVisibility(View.GONE);
        mChooseIv.setImageResource(0);
    }

    @OnClick(R.id.tv_video_type)
    void videoType() {
        chooseType = 2;
        mChooseHintTv.setText("选择视频");
        mImageTypeTv.setBackgroundResource(R.drawable.choose_type_normal);
        mImageTypeTv.setTextColor(ContextCompat.getColor(this, R.color.black));

        mVideoTypeTv.setBackgroundResource(R.drawable.choose_type_selected);
        mVideoTypeTv.setTextColor(ContextCompat.getColor(this, R.color.white));
        mVideoLayout.setVisibility(View.VISIBLE);
        mChooseIv.setImageResource(0);
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

    @OnClick(R.id.btn_config)
    void config() {
        if (StringUtils.isEmpty(imagePath) && StringUtils.isEmpty(videoPath)) {
            ToastUtils.showLong("请选择图片或者视频");
            return;
        }

        if (isQunLiao) {
            if (StringUtils.isEmpty(sendUserName)) {
                ToastUtils.showLong("请选择发送人");
                return;
            }

            CHAT_TYPE = MessageContent.SEND_IMAGE;
            if (chooseType == 1) {
                if (chooseRolePos > 0) {
                    CHAT_TYPE = MessageContent.RECEIVE_IMAGE;
                }
            } else {
                CHAT_TYPE = MessageContent.SEND_IMAGE_FOR_VIDEO;
                if (chooseRolePos > 0) {
                    CHAT_TYPE = MessageContent.RECEIVE_IMAGE_FOR_VIDEO;
                }
            }

            //插入一条时间设置记录
            ImageMessage imageMessage = new ImageMessage();
            imageMessage.setWxMainId(App.getApp().getMessageContent().getWxMainId());
            imageMessage.setMessageType(CHAT_TYPE);
            imageMessage.setMediaType(chooseType);
            imageMessage.setMessageUserName(sendUserName);
            imageMessage.setMessageUserHead(sendUserHead);
            imageMessage.setImageUrl(chooseType == 1 ? imagePath : videoPath);
            imageMessage.setVideoTime(chooseType == 2 ? mVideoTimeTv.getText().toString() : "0:0");
            imageMessage.setLocalMessageImg(R.mipmap.type_image);
            Long imageId = mAppDatabase.imageMessageDao().insert(imageMessage);

            //插入到外层的列表中
            WeixinQunChatInfo weixinQunChatInfo = new WeixinQunChatInfo();
            weixinQunChatInfo.setWxMainId(App.getApp().getMessageContent().getWxMainId());
            weixinQunChatInfo.setTypeIcon(chooseType == 1 ? R.mipmap.type_image : R.mipmap.type_video);
            weixinQunChatInfo.setType(CHAT_TYPE);
            weixinQunChatInfo.setChildTabId(imageId);
            mAppDatabase.weixinQunChatInfoDao().insert(weixinQunChatInfo);
        } else {

            CHAT_TYPE = MessageContent.SEND_IMAGE;
            if (chooseType == 1) {
                if (!SPUtils.getInstance().getBoolean(Constants.IS_SELF, true)) {
                    CHAT_TYPE = MessageContent.RECEIVE_IMAGE;
                }
            } else {
                CHAT_TYPE = MessageContent.SEND_IMAGE_FOR_VIDEO;
                if (!SPUtils.getInstance().getBoolean(Constants.IS_SELF, true)) {
                    CHAT_TYPE = MessageContent.RECEIVE_IMAGE_FOR_VIDEO;
                }
            }

            //插入一条时间设置记录
            ImageMessage imageMessage = new ImageMessage();
            imageMessage.setWxMainId(App.getApp().getMessageContent().getWxMainId());
            imageMessage.setMessageType(CHAT_TYPE);
            imageMessage.setMediaType(chooseType);
            imageMessage.setMessageUserName(isMySelf ? App.getApp().chatDataInfo.getPersonName() : App.getApp().chatDataInfo.getOtherPersonName());
            imageMessage.setMessageUserHead(isMySelf ? App.getApp().chatDataInfo.getPersonHead() : App.getApp().chatDataInfo.getOtherPersonHead());
            imageMessage.setImageUrl(chooseType == 1 ? imagePath : videoPath);
            imageMessage.setVideoTime(chooseType == 2 ? mVideoTimeTv.getText().toString() : "0:0");
            imageMessage.setLocalMessageImg(R.mipmap.type_image);
            Long imageId = mAppDatabase.imageMessageDao().insert(imageMessage);

            //插入到外层的列表中
            WeixinChatInfo weixinChatInfo = new WeixinChatInfo();
            weixinChatInfo.setWxMainId(App.getApp().getMessageContent().getWxMainId());
            weixinChatInfo.setTypeIcon(chooseType == 1 ? R.mipmap.type_image : R.mipmap.type_video);
            weixinChatInfo.setType(CHAT_TYPE);
            weixinChatInfo.setChildTabId(imageId);
            mAppDatabase.weixinChatInfoDao().insert(weixinChatInfo);
        }

        finish();
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

    @OnClick(R.id.iv_back)
    void back() {
        finish();
    }

    @Override
    public void chooseRole(int pos, String name, String head, int localHead) {
        chooseRolePos = pos;

        CHAT_TYPE = MessageContent.SEND_IMAGE;
        if (chooseType == 1) {
            if (chooseRolePos > 0) {
                CHAT_TYPE = MessageContent.RECEIVE_IMAGE;
            }
        } else {
            CHAT_TYPE = MessageContent.SEND_IMAGE_FOR_VIDEO;
            if (chooseRolePos > 0) {
                CHAT_TYPE = MessageContent.RECEIVE_IMAGE_FOR_VIDEO;
            }
        }

        mSendUserNameTv.setText(name);
        sendUserName = name;
        Glide.with(this).load(head).into(mSendUserHeadIv);
        sendUserHead = head;
    }
}
