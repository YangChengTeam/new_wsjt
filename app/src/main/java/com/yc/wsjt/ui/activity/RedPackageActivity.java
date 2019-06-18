package com.yc.wsjt.ui.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
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
import com.blankj.utilcode.util.PathUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.bumptech.glide.Glide;
import com.jaeger.library.StatusBarUtil;
import com.orhanobut.logger.Logger;
import com.yc.wsjt.App;
import com.yc.wsjt.R;
import com.yc.wsjt.common.Constants;
import com.yc.wsjt.presenter.Presenter;
import com.yc.wsjt.ui.custom.EmojiModeDialog;
import com.yc.wsjt.ui.custom.Glide4Engine;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

public class RedPackageActivity extends BaseActivity implements EmojiModeDialog.ModeClickListener {

    private static final int REQUEST_CODE_CHOOSE = 1000;

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

    private File outputImage;

    private int chooseType = 1;//发(1)，收

    EmojiModeDialog emojiModeDialog;

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
        emojiModeDialog = new EmojiModeDialog(this, R.style.scale_dialog, "对方表情");
        emojiModeDialog.setModeClickListener(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        if (App.getApp().chatDataInfo != null) {
            boolean isMySelf = SPUtils.getInstance().getBoolean(Constants.IS_SELF, true);
            mSendUserNameTv.setText(isMySelf ? App.getApp().chatDataInfo.getPersonName() : App.getApp().chatDataInfo.getOtherPersonName());
            Glide.with(this).load(isMySelf ? App.getApp().chatDataInfo.getPersonHead() : App.getApp().chatDataInfo.getOtherPersonHead()).into(mSendUserHeadIv);
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
        boolean isMySelf = SPUtils.getInstance().getBoolean(Constants.IS_SELF, true);
        SPUtils.getInstance().put(Constants.IS_SELF, !isMySelf);
        mSendUserNameTv.setText(!isMySelf ? App.getApp().chatDataInfo.getPersonName() : App.getApp().chatDataInfo.getOtherPersonName());
        Glide.with(this).load(!isMySelf ? App.getApp().chatDataInfo.getPersonHead() : App.getApp().chatDataInfo.getOtherPersonHead()).into(mSendUserHeadIv);
    }

    @OnClick(R.id.tv_send_red)
    void sendRed() {
        mSendTypeTv.setBackgroundResource(R.drawable.choose_type_selected);
        mSendTypeTv.setTextColor(ContextCompat.getColor(this, R.color.white));

        mReceiveTypeTv.setBackgroundResource(R.drawable.choose_type_normal);
        mReceiveTypeTv.setTextColor(ContextCompat.getColor(this, R.color.black));
        mGetUserInfoLayout.setVisibility(View.GONE);
        mOtherSideIv.setImageResource(0);
    }

    @OnClick(R.id.tv_receive_red)
    void receiveRed() {
        mSendTypeTv.setBackgroundResource(R.drawable.choose_type_normal);
        mSendTypeTv.setTextColor(ContextCompat.getColor(this, R.color.black));

        mReceiveTypeTv.setBackgroundResource(R.drawable.choose_type_selected);
        mReceiveTypeTv.setTextColor(ContextCompat.getColor(this, R.color.white));
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

    @OnClick(R.id.btn_config)
    void config() {
        finish();
    }
}
