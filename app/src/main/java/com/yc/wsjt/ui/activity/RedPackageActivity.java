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
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.jaeger.library.StatusBarUtil;
import com.orhanobut.logger.Logger;
import com.yc.wsjt.App;
import com.yc.wsjt.R;
import com.yc.wsjt.presenter.Presenter;
import com.yc.wsjt.ui.custom.EmojiModeDialog;
import com.yc.wsjt.ui.custom.Glide4Engine;
import com.yc.wsjt.ui.custom.SettingRoleDialog;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;

import java.io.File;
import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.OnClick;

public class RedPackageActivity extends BaseActivity implements EmojiModeDialog.ModeClickListener {

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

    @BindView(R.id.layout_get_user_info)
    LinearLayout mGetUserInfoLayout;

    @BindView(R.id.et_red_number)
    EditText mRedNumberEt;

    @BindView(R.id.et_red_remark)
    EditText mRedRemarkEt;

    @BindView(R.id.tv_get_user_name)
    TextView mGetUserNameTv;

    @BindView(R.id.iv_get_user_head)
    ImageView mGetUserHeadIv;

    private File outputImage;

    private int chooseType = 1;

    EmojiModeDialog emojiModeDialog;

    private boolean isSend;//是否是发红包,默认是收红包

    SettingRoleDialog settingRoleDialog;

    private String sendUserName;

    private String sendUserHead;

    private String receiveUserName;

    private String receiveUserHead;

    private int chooseRole = 1;//发送人,领红包人

    @Override
    protected int getLayoutId() {
        return R.layout.activity_red_package;
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
        mTitleTv.setText("微信红包");
        emojiModeDialog = new EmojiModeDialog(this, R.style.scale_dialog, "对方表情");
        emojiModeDialog.setModeClickListener(this);

        settingRoleDialog = new SettingRoleDialog(this, R.style.custom_dialog);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        if (App.getApp().chatDataInfo != null) {
            //boolean isMySelf = SPUtils.getInstance().getBoolean(Constants.IS_SELF, true);
            sendUserName = App.getApp().chatDataInfo.getPersonName() != null ? App.getApp().chatDataInfo.getPersonName() : "";
            receiveUserName = App.getApp().chatDataInfo.getOtherPersonName() != null ? App.getApp().chatDataInfo.getOtherPersonName() : "";
            mSendUserNameTv.setText(sendUserName);
            mGetUserNameTv.setText(receiveUserName);

            //发送人
            if (StringUtils.isEmpty(App.getApp().chatDataInfo.getPersonHead())) {
                Glide.with(this).load(R.mipmap.user_head_def).into(mSendUserHeadIv);
            } else {
                Glide.with(this).load(App.getApp().chatDataInfo.getPersonHead()).into(mSendUserHeadIv);
            }
            sendUserHead = App.getApp().chatDataInfo.getPersonHead();

            //领红包人
            if (StringUtils.isEmpty(App.getApp().chatDataInfo.getOtherPersonHead())) {
                Glide.with(this).load(R.mipmap.user_head_def).into(mGetUserHeadIv);
            } else {
                Glide.with(this).load(App.getApp().chatDataInfo.getOtherPersonHead()).into(mGetUserHeadIv);
            }
            receiveUserHead = App.getApp().chatDataInfo.getOtherPersonHead();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (App.getApp().getTempPerson() != null) {
            if (chooseRole == 1) {
                mSendUserNameTv.setText(App.getApp().getTempPerson().mName);
                Glide.with(this).load(App.getApp().getTempPerson().mHead).into(mSendUserHeadIv);
                sendUserName = App.getApp().getTempPerson().mName;
                sendUserHead = App.getApp().getTempPerson().mHead;
            } else {
                mGetUserNameTv.setText(App.getApp().getTempPerson().mName);
                Glide.with(this).load(App.getApp().getTempPerson().mHead).into(mGetUserHeadIv);
                receiveUserName = App.getApp().getTempPerson().mName;
                receiveUserHead = App.getApp().getTempPerson().mHead;
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
                        Logger.i("out path--->" + outputImage.getAbsolutePath());
                        Glide.with(RedPackageActivity.this).load(Matisse.obtainResult(data).get(0)).into(chooseType == 1 ? mOtherSideIv : mReplyEmojiIv);
                    }
                    break;
            }
        }
    }

    @OnClick(R.id.layout_send_info)
    void changeSendRole() {
        chooseRole = 1;
        if (settingRoleDialog != null && !settingRoleDialog.isShowing()) {
            settingRoleDialog.setType(2);
            settingRoleDialog.show();
        }
    }

    @OnClick(R.id.layout_receive_user)
    void chooseReceiveUser() {
        chooseRole = 2;
        if (settingRoleDialog != null && !settingRoleDialog.isShowing()) {
            settingRoleDialog.setType(2);
            settingRoleDialog.show();
        }
    }

    @OnClick(R.id.tv_receive_red)
    void receiveRed() {
        isSend = false;
        mSendTypeTv.setBackgroundResource(R.drawable.choose_type_normal);
        mSendTypeTv.setTextColor(ContextCompat.getColor(this, R.color.add_chat_color));

        mReceiveTypeTv.setBackgroundResource(R.drawable.choose_type_selected);
        mReceiveTypeTv.setTextColor(ContextCompat.getColor(this, R.color.white));
        mGetUserInfoLayout.setVisibility(View.GONE);
        mOtherSideIv.setImageResource(0);
    }

    @OnClick(R.id.tv_send_red)
    void sendRed() {
        isSend = true;
        mSendTypeTv.setBackgroundResource(R.drawable.choose_type_selected);
        mSendTypeTv.setTextColor(ContextCompat.getColor(this, R.color.white));

        mReceiveTypeTv.setBackgroundResource(R.drawable.choose_type_normal);
        mReceiveTypeTv.setTextColor(ContextCompat.getColor(this, R.color.add_chat_color));
        mGetUserInfoLayout.setVisibility(View.VISIBLE);
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

    @OnClick(R.id.btn_show_pre)
    void redPreShow() {
        if (isSend) {
            if (StringUtils.isEmpty(sendUserName)) {
                ToastUtils.showLong("请选择发送人");
                return;
            }

            if (StringUtils.isEmpty(mRedNumberEt.getText())) {
                ToastUtils.showLong("请输入金额");
                return;
            }

            Intent intent = new Intent(this, RedSendPreActivity.class);

            intent.putExtra("send_user_name", sendUserName);
            intent.putExtra("send_user_head", sendUserHead);
            DecimalFormat df = new DecimalFormat(".00");
            String temp = df.format(Double.parseDouble(mRedNumberEt.getText().toString()));
            intent.putExtra("red_money", temp);
            intent.putExtra("red_remark", StringUtils.isEmpty(mRedRemarkEt.getText()) ? "恭喜发财，大吉大利" : mRedRemarkEt.getText().toString());
            intent.putExtra("red_receive_date", "12:33");
            intent.putExtra("receive_user_name", StringUtils.isEmpty(receiveUserName) ? "未知用户" : receiveUserName);
            intent.putExtra("receive_user_head", receiveUserHead);
            startActivity(intent);
        } else {
            if (StringUtils.isEmpty(sendUserName)) {
                ToastUtils.showLong("请选择发送人");
                return;
            }

            if (StringUtils.isEmpty(mRedNumberEt.getText())) {
                ToastUtils.showLong("请输入金额");
                return;
            }

            Intent intent = new Intent(this, RedReceivePreActivity.class);
            intent.putExtra("send_user_name", sendUserName);
            intent.putExtra("send_user_head", sendUserHead);
            DecimalFormat df = new DecimalFormat(".00");
            String temp = df.format(Double.parseDouble(mRedNumberEt.getText().toString()));
            intent.putExtra("red_money", temp);
            intent.putExtra("red_remark", StringUtils.isEmpty(mRedRemarkEt.getText()) ? "恭喜发财，大吉大利" : mRedRemarkEt.getText().toString());

            startActivity(intent);
        }
    }
}
