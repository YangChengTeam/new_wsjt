package com.yc.wsjt.ui.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.PathUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.jaeger.library.StatusBarUtil;
import com.orhanobut.logger.Logger;
import com.yc.wsjt.App;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.MessageContent;
import com.yc.wsjt.bean.RedPackageMessage;
import com.yc.wsjt.bean.WeixinChatInfo;
import com.yc.wsjt.bean.WeixinQunChatInfo;
import com.yc.wsjt.common.Constants;
import com.yc.wsjt.presenter.Presenter;
import com.yc.wsjt.ui.custom.EmojiModeDialog;
import com.yc.wsjt.ui.custom.Glide4Engine;
import com.yc.wsjt.ui.custom.ProposeDialog;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

public class ChatRedPackageActivity extends BaseActivity implements EmojiModeDialog.ModeClickListener, ProposeDialog.ProposeListener {

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

    @BindView(R.id.layout_other_side_emoji)
    RelativeLayout mChooseImgLayout;

    @BindView(R.id.iv_other_side_img)
    ImageView mOtherSideIv;

    @BindView(R.id.iv_reply_image)
    ImageView mReplyEmojiIv;

    @BindView(R.id.tv_send_red)
    TextView mSendTypeTv;

    @BindView(R.id.tv_receive_red)
    TextView mReceiveTypeTv;

    @BindView(R.id.layout_red_number)
    LinearLayout mRedNumberLayout;

    @BindView(R.id.et_red_number)
    EditText mRedNumberEt;

    @BindView(R.id.et_red_remark)
    EditText mRedRemarkEt;

    private File outputImage;

    private String otherSideImgUrl;

    private String replyImgUrl;

    private int chooseType = 1;//发，收

    EmojiModeDialog emojiModeDialog;

    private int fromType;//1.聊天中设置界面。2，单独跳转操作页面

    boolean isMySelf = true;

    private boolean isQunLiao;

    ProposeDialog proposeDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_chat_red_package;
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
        emojiModeDialog = new EmojiModeDialog(this, R.style.scale_dialog, "对方表情");
        emojiModeDialog.setModeClickListener(this);
        proposeDialog = new ProposeDialog(this, R.style.custom_dialog);
        proposeDialog.setProposeListener(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            isQunLiao = bundle.getBoolean("is_qunliao", false);
        }
        mTitleTv.setText(isQunLiao ? "群聊红包" : "单聊红包");

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

    @OnClick(R.id.layout_other_side_emoji)
    void otherSideEmoji() {
        chooseType = 1;
        emojiModeDialog.show();
        WindowManager.LayoutParams windowParams = emojiModeDialog.getWindow().getAttributes();
        windowParams.width = (int) (ScreenUtils.getScreenWidth() * 0.75);
        windowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        emojiModeDialog.getWindow().setAttributes(windowParams);
    }

    @OnClick(R.id.layout_reply_emoji)
    void replyEmoji() {
        chooseType = 2;

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
                    if (Matisse.obtainResult(data) != null && Matisse.obtainResult(data).size() > 0) {
                        outputImage = new File(PathUtils.getExternalAppPicturesPath(), TimeUtils.getNowMills() + ".png");
                        if (chooseType == 1) {
                            otherSideImgUrl = outputImage.getAbsolutePath();
                        } else {
                            replyImgUrl = outputImage.getAbsolutePath();
                        }
                        Logger.i("out path--->" + outputImage.getAbsolutePath());
                        Glide.with(ChatRedPackageActivity.this).load(Matisse.obtainResult(data).get(0)).into(chooseType == 1 ? mOtherSideIv : mReplyEmojiIv);
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

    @OnClick(R.id.tv_send_red)
    void sendRed() {
        chooseType = 1;
        mSendTypeTv.setBackgroundResource(R.drawable.choose_type_selected);
        mSendTypeTv.setTextColor(ContextCompat.getColor(this, R.color.white));

        mReceiveTypeTv.setBackgroundResource(R.drawable.choose_type_normal);
        mReceiveTypeTv.setTextColor(ContextCompat.getColor(this, R.color.black));
        mRedNumberLayout.setVisibility(View.VISIBLE);
        mOtherSideIv.setImageResource(0);
    }

    @OnClick(R.id.tv_receive_red)
    void receiveRed() {
        chooseType = 2;
        mSendTypeTv.setBackgroundResource(R.drawable.choose_type_normal);
        mSendTypeTv.setTextColor(ContextCompat.getColor(this, R.color.black));

        mReceiveTypeTv.setBackgroundResource(R.drawable.choose_type_selected);
        mReceiveTypeTv.setTextColor(ContextCompat.getColor(this, R.color.white));
        mRedNumberLayout.setVisibility(View.GONE);
        mOtherSideIv.setImageResource(0);
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
        Glide.with(this).load(R.mipmap.user_head_def).into(chooseType == 1 ? mOtherSideIv : mReplyEmojiIv);
    }

    @OnClick(R.id.btn_config)
    void config() {
        if (chooseType == 1) {
            if (StringUtils.isEmpty(mRedNumberEt.getText())) {
                ToastUtils.showLong("请输入金额");
                return;
            }
        }

        if (chooseType == 1 && Double.parseDouble(mRedNumberEt.getText().toString()) > 200) {
            proposeDialog.show();
            WindowManager.LayoutParams windowParams = proposeDialog.getWindow().getAttributes();
            windowParams.width = (int) (ScreenUtils.getScreenWidth() * 0.75);
            windowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
            proposeDialog.getWindow().setAttributes(windowParams);
            return;
        }

        insertData();
    }

    public void insertData() {
        String robInfo = "你领取了" + App.getApp().chatDataInfo.getOtherPersonName() + "的";
        int type = MessageContent.SEND_RED_PACKET;
        if (!SPUtils.getInstance().getBoolean(Constants.IS_SELF, true)) {
            type = MessageContent.RECEIVE_RED_PACKET;
            robInfo = App.getApp().chatDataInfo.getOtherPersonName() + "领取了你的";
        }

        Long redId = 0L;
        if (chooseType == 1) {
            RedPackageMessage redPackageMessage = new RedPackageMessage();
            //redPackageMessage.setWxMainId(App.getApp().getMessageContent().getWxMainId());
            redPackageMessage.setRedNumber(mRedNumberEt.getText().toString());
            redPackageMessage.setRedDesc(StringUtils.isEmpty(mRedRemarkEt.getText()) ? mRedRemarkEt.getText().toString() : "恭喜发财，大吉大利!");
            redPackageMessage.setMessageType(type);
            redPackageMessage.setRedType(chooseType);
            redPackageMessage.setMessageUserName(isMySelf ? App.getApp().chatDataInfo.getPersonName() : App.getApp().chatDataInfo.getOtherPersonName());
            redPackageMessage.setMessageUserHead(isMySelf ? App.getApp().chatDataInfo.getPersonHead() : App.getApp().chatDataInfo.getOtherPersonHead());
            redPackageMessage.setOtherSideEmojiUrl(otherSideImgUrl);
            redPackageMessage.setReplyEmojiUrl(replyImgUrl);
            redPackageMessage.setLocalMessageImg(R.mipmap.type_hongbao);
            redId = mAppDatabase.redMessageDao().insert(redPackageMessage);
        } else {
            type = MessageContent.ROB_RED_PACKET;

            RedPackageMessage redPackageMessage = new RedPackageMessage();
            redPackageMessage.setMessageType(type);
            redPackageMessage.setRedType(chooseType);
            redPackageMessage.setRobInfo(robInfo);
            redId = mAppDatabase.redMessageDao().insert(redPackageMessage);
        }

        if (isQunLiao) {
            //插入到外层的列表中
            WeixinQunChatInfo weixinQunChatInfo = new WeixinQunChatInfo();
            weixinQunChatInfo.setMainId(App.getApp().getMessageContent().getId());
            weixinQunChatInfo.setTypeIcon(R.mipmap.type_hongbao);
            weixinQunChatInfo.setType(type);
            weixinQunChatInfo.setChildTabId(redId);
            mAppDatabase.weixinQunChatInfoDao().insert(weixinQunChatInfo);
        } else {
            //插入到外层的列表中
            WeixinChatInfo weixinChatInfo = new WeixinChatInfo();
            weixinChatInfo.setMainId(App.getApp().getMessageContent().getId());
            weixinChatInfo.setTypeIcon(R.mipmap.type_hongbao);
            weixinChatInfo.setType(type);
            weixinChatInfo.setChildTabId(redId);
            mAppDatabase.weixinChatInfoDao().insert(weixinChatInfo);
        }

        finish();
    }

    @Override
    public void continueUse() {
        insertData();
    }

    @Override
    public void cancelUse() {

    }
}
