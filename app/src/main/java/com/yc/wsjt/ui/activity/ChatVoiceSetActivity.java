package com.yc.wsjt.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.yc.wsjt.App;
import com.yc.wsjt.R;
import com.yc.wsjt.presenter.Presenter;
import com.yc.wsjt.ui.custom.SettingRoleDialog;
import com.yc.wsjt.ui.custom.VideoTimeDialog;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhangdinghui on 2019/5/28.
 */
public class ChatVoiceSetActivity extends BaseActivity implements VideoTimeDialog.DateSelectListener {

    private static final int REQUEST_CODE_CHOOSE = 1000;

    @BindView(R.id.tv_title)
    TextView mTitleTv;

    @BindView(R.id.btn_config)
    Button mConfigBtn;

    @BindView(R.id.tv_wait_voice)
    TextView mWaitVoiceTv;

    @BindView(R.id.tv_voice_ing)
    TextView mVoiceIngTv;

    @BindView(R.id.tv_target_user_name)
    TextView mTargetUserNameTv;

    @BindView(R.id.iv_target_user_head)
    ImageView mTargetUserHeadIv;

    @BindView(R.id.layout_voice_time)
    LinearLayout mVoiceTimeLayout;

    @BindView(R.id.tv_connection_time)
    TextView mConnectionTimeTv;

    SettingRoleDialog settingRoleDialog;

    private File outputImage;

    VideoTimeDialog videoTimeDialog;

    private int chooseType = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_voice_setting;
    }

    @Override
    protected Presenter getPresenter() {
        return null;
    }

    @Override
    protected void initVars() {
        mTitleTv.setText("微信语音聊天");
        mConfigBtn.setVisibility(View.GONE);

        videoTimeDialog = new VideoTimeDialog(this, R.style.video_time_dialog, 60);
        videoTimeDialog.setDateSelectListener(this);
    }

    @Override
    protected void initViews() {
        settingRoleDialog = new SettingRoleDialog(this, R.style.custom_dialog);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (App.getApp().getTempPerson() != null) {
            mTargetUserNameTv.setText(App.getApp().getTempPerson().mName);
            Glide.with(this).load(App.getApp().getTempPerson().mHead).into(mTargetUserHeadIv);
        }
    }

    @OnClick(R.id.tv_wait_voice)
    void imageType() {
        mWaitVoiceTv.setBackgroundResource(R.drawable.choose_type_selected);
        mWaitVoiceTv.setTextColor(ContextCompat.getColor(this, R.color.white));

        mVoiceIngTv.setBackgroundResource(R.drawable.choose_type_normal);
        mVoiceIngTv.setTextColor(ContextCompat.getColor(this, R.color.black));
        mVoiceTimeLayout.setVisibility(View.GONE);
    }

    @OnClick(R.id.tv_voice_ing)
    void videoType() {
        mWaitVoiceTv.setBackgroundResource(R.drawable.choose_type_normal);
        mWaitVoiceTv.setTextColor(ContextCompat.getColor(this, R.color.black));

        mVoiceIngTv.setBackgroundResource(R.drawable.choose_type_selected);
        mVoiceIngTv.setTextColor(ContextCompat.getColor(this, R.color.white));
        mVoiceTimeLayout.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.layout_target)
    void targetUserInfo() {
        if (settingRoleDialog != null && !settingRoleDialog.isShowing()) {
            settingRoleDialog.setType(2);
            settingRoleDialog.show();
        }
    }

    @OnClick(R.id.layout_voice_time)
    void videoTime() {
        videoTimeDialog.show();

        WindowManager.LayoutParams windowParams = videoTimeDialog.getWindow().getAttributes();
        windowParams.width = (int) (ScreenUtils.getScreenWidth() * 0.7);
        windowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        videoTimeDialog.getWindow().setAttributes(windowParams);
    }

    @Override
    public void configDate(String selectDate) {
        if (!StringUtils.isEmpty(selectDate)) {
            mConnectionTimeTv.setText(selectDate);
        }
    }

    @OnClick(R.id.btn_pre_show)
    void preShow() {
        if (mTargetUserNameTv.getText().equals("请选择")) {
            ToastUtils.showLong("请选择通话对象");
            return;
        }
        if (mVoiceTimeLayout.getVisibility() == View.GONE) {
            //待接听
            Intent intent = new Intent(this, ChatWaitVoicePreActivity.class);
            startActivity(intent);
        } else {

            if (mConnectionTimeTv.getText().equals("00:00")) {
                ToastUtils.showLong("请设置通话时长");
                return;
            }

            //通话中
            Intent intent = new Intent(this, ChatVoiceingPreActivity.class);
            intent.putExtra("connect_time", mConnectionTimeTv.getText().toString());
            startActivity(intent);
        }
    }

    @OnClick(R.id.iv_back)
    void back() {
        finish();
    }
}
