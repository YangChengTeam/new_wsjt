package com.yc.wsjt.ui.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import com.yc.wsjt.bean.MessageContent;
import com.yc.wsjt.bean.ShareMessage;
import com.yc.wsjt.bean.WeixinChatInfo;
import com.yc.wsjt.bean.WeixinQunChatInfo;
import com.yc.wsjt.common.Constants;
import com.yc.wsjt.presenter.Presenter;
import com.yc.wsjt.ui.custom.EmojiModeDialog;
import com.yc.wsjt.ui.custom.Glide4Engine;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;

import butterknife.BindView;
import butterknife.OnClick;

public class ChatShareActivity extends BaseActivity implements EmojiModeDialog.ModeClickListener {

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

    @BindView(R.id.layout_share_img)
    RelativeLayout mShareImgLayout;

    @BindView(R.id.iv_share_img)
    ImageView mShareIv;

    @BindView(R.id.et_share_title)
    EditText mShareTitleEt;

    @BindView(R.id.et_share_content)
    EditText mShareContentEt;

    private String thumbPath;

    private int chooseType = 1;

    EmojiModeDialog emojiModeDialog;

    boolean isMySelf = true;

    private boolean isQunLiao;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_chat_share;
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
        emojiModeDialog = new EmojiModeDialog(this, R.style.scale_dialog, "选择文章封面");
        emojiModeDialog.setModeClickListener(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            isQunLiao = bundle.getBoolean("is_qunliao", false);
        }
        mTitleTv.setText(isQunLiao ? "群聊分享" : "单聊分享");

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

    @OnClick(R.id.layout_share_img)
    void shareImage() {
        chooseType = 1;

        emojiModeDialog.show();
        WindowManager.LayoutParams windowParams = emojiModeDialog.getWindow().getAttributes();
        windowParams.width = (int) (ScreenUtils.getScreenWidth() * 0.75);
        windowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        emojiModeDialog.getWindow().setAttributes(windowParams);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_CHOOSE:
                    Logger.i(JSONObject.toJSONString(Matisse.obtainPathResult(data)));
                    if (Matisse.obtainPathResult(data) != null && Matisse.obtainPathResult(data).size() > 0) {
                        thumbPath = Matisse.obtainPathResult(data).get(0);
                        Glide.with(ChatShareActivity.this).load(thumbPath).into(mShareIv);
                    }
                    break;
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

    @Override
    public void customEmoji() {
        Matisse.from(this)
                .choose(MimeType.ofAll())
                .countable(true)
                .maxSelectable(1)
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(new Glide4Engine())
                .showSingleMediaType(true)
                .forResult(REQUEST_CODE_CHOOSE);
    }

    @Override
    public void noneEmoji() {
        Glide.with(this).load(R.mipmap.user_head_def).into(mShareIv);
    }

    @OnClick(R.id.btn_config)
    void config() {
        if (StringUtils.isEmpty(thumbPath)) {
            ToastUtils.showLong("请选择封面图片");
            return;
        }

        if (StringUtils.isEmpty(mShareTitleEt.getText())) {
            ToastUtils.showLong("请输入标题");
            return;
        }

        if (StringUtils.isEmpty(mShareContentEt.getText())) {
            ToastUtils.showLong("请输入内容");
            return;
        }

        int type = MessageContent.SEND_SHARE;
        if (!SPUtils.getInstance().getBoolean(Constants.IS_SELF, true)) {
            type = MessageContent.RECEIVE_SHARE;
        }

        //插入一条时间设置记录
        ShareMessage shareMessage = new ShareMessage();
        shareMessage.setWxMainId(App.getApp().getMessageContent().getWxMainId());
        shareMessage.setMessageType(type);
        shareMessage.setShareThumb(StringUtils.isEmpty(thumbPath) ? "" : thumbPath);
        shareMessage.setShareTitle(mShareTitleEt.getText().toString());
        shareMessage.setShareContent(mShareContentEt.getText().toString());
        shareMessage.setMessageUserName(isMySelf ? App.getApp().chatDataInfo.getPersonName() : App.getApp().chatDataInfo.getOtherPersonName());
        shareMessage.setMessageUserHead(isMySelf ? App.getApp().chatDataInfo.getPersonHead() : App.getApp().chatDataInfo.getOtherPersonHead());

        Long shareId = mAppDatabase.shareMessageDao().insert(shareMessage);

        if (isQunLiao) {
            //插入到外层的列表中
            WeixinQunChatInfo weixinQunChatInfo = new WeixinQunChatInfo();
            weixinQunChatInfo.setWxMainId(App.getApp().getMessageContent().getWxMainId());
            weixinQunChatInfo.setTypeIcon(R.mipmap.type_share);
            weixinQunChatInfo.setType(type);
            weixinQunChatInfo.setChildTabId(shareId);
            mAppDatabase.weixinQunChatInfoDao().insert(weixinQunChatInfo);
        } else {
            //插入到外层的列表中
            WeixinChatInfo weixinChatInfo = new WeixinChatInfo();
            weixinChatInfo.setWxMainId(App.getApp().getMessageContent().getWxMainId());
            weixinChatInfo.setTypeIcon(R.mipmap.type_share);
            weixinChatInfo.setType(type);
            weixinChatInfo.setChildTabId(shareId);

            mAppDatabase.weixinChatInfoDao().insert(weixinChatInfo);
        }
        finish();
    }

    @OnClick(R.id.iv_back)
    void back() {
        finish();
    }
}
