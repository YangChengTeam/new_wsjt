package com.yc.wsjt.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.PathUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.jaeger.library.StatusBarUtil;
import com.orhanobut.logger.Logger;
import com.othershe.combinebitmap.CombineBitmap;
import com.othershe.combinebitmap.layout.WechatLayoutManager;
import com.othershe.combinebitmap.listener.OnProgressListener;
import com.yc.wsjt.App;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.GroupMessage;
import com.yc.wsjt.bean.MessageContent;
import com.yc.wsjt.bean.WeixinChatInfo;
import com.yc.wsjt.common.Constants;
import com.yc.wsjt.presenter.Presenter;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

public class ChatGroupActivity extends BaseActivity {

    public static final int REQUEST_CODE_EMOJI = 1;

    @BindView(R.id.btn_config)
    Button mConfigBtn;

    @BindView(R.id.layout_send_info)
    RelativeLayout mSendInfoLayout;

    @BindView(R.id.tv_send_user_name)
    TextView mSendUserNameTv;

    @BindView(R.id.iv_send_user_head)
    ImageView mSendUserHeadIv;

    @BindView(R.id.iv_group_head)
    ImageView mGroupHeadIv;

    @BindView(R.id.et_group_name)
    EditText mGroupNameEt;

    private File outputImage;

    public int[] images = null;

    boolean isMySelf = true;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_chat_group;
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

    @OnClick(R.id.layout_group_head)
    void setGroupHead() {
        Intent intent = new Intent(this, ChatGroupSetActivity.class);
        //startActivity(intent);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Logger.i("onActivityResult--->" + requestCode + "---" + resultCode);
        if (requestCode == 0 && resultCode == RESULT_OK) {
            if (data.getExtras().getIntArray("group_images") != null) {
                images = data.getExtras().getIntArray("group_images");
                CombineBitmap.init(context)
                        .setLayoutManager(new WechatLayoutManager()) // 必选， 设置图片的组合形式，支持WechatLayoutManager、DingLayoutManager
                        .setSize(100) // 必选，组合后Bitmap的尺寸，单位dp
                        .setGap(1) // 单个图片之间的距离，单位dp，默认0dp
                        .setResourceIds(images)
                        .setImageView(mGroupHeadIv)//直接设置要显示图片的ImageView
                        .setOnProgressListener(new OnProgressListener() {
                            @Override
                            public void onStart() {

                            }

                            @Override
                            public void onComplete(Bitmap bitmap) {
                                outputImage = new File(PathUtils.getExternalAppPicturesPath(), TimeUtils.getNowMills() + ".png");
                                Logger.i("out path--->" + outputImage.getAbsolutePath());
                                ImageUtils.save(bitmap, outputImage, Bitmap.CompressFormat.PNG);
                            }
                        })
                        .build();
            }
        }
    }

    @OnClick(R.id.btn_config)
    void config() {
        if (StringUtils.isEmpty(mGroupNameEt.getText())) {
            ToastUtils.showLong("请输入群名称");
            return;
        }

        if (images == null || images.length == 0) {
            ToastUtils.showLong("请选择群头像");
            return;
        }

        int type = MessageContent.SEND_JOIN_GROUP;
        if (!SPUtils.getInstance().getBoolean(Constants.IS_SELF, true)) {
            type = MessageContent.RECEIVE_JOIN_GROUP;
        }

        //插入一条时间设置记录
        GroupMessage groupMessage = new GroupMessage();
        groupMessage.setWxMainId(App.getApp().getMessageContent().getWxMainId());
        groupMessage.setGroupHead(!StringUtils.isEmpty(outputImage.getAbsolutePath()) && outputImage.exists() ? outputImage.getAbsolutePath() : "");
        String tempUserName = App.getApp().mUserInfo != null ? App.getApp().mUserInfo.getNickName() : "未知用户";
        groupMessage.setMessageContent("\"" +tempUserName + "\"邀请你加入群聊" + mGroupNameEt.getText().toString() + ",进入可查看详情。");
        groupMessage.setMessageType(type);
        groupMessage.setGroupName(mGroupNameEt.getText().toString());
        Long groupId = mAppDatabase.groupMessageDao().insert(groupMessage);

        //插入到外层的列表中
        WeixinChatInfo weixinChatInfo = new WeixinChatInfo();
        weixinChatInfo.setWxMainId(App.getApp().getMessageContent().getWxMainId());
        weixinChatInfo.setTypeIcon(R.mipmap.type_join_group);
        weixinChatInfo.setType(type);
        weixinChatInfo.setChildTabId(groupId);

        mAppDatabase.weixinChatInfoDao().insert(weixinChatInfo);

        finish();
    }

    @OnClick(R.id.iv_back)
    void back() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
