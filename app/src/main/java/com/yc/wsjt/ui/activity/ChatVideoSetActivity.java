package com.yc.wsjt.ui.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.orhanobut.logger.Logger;
import com.yc.wsjt.App;
import com.yc.wsjt.R;
import com.yc.wsjt.presenter.Presenter;
import com.yc.wsjt.ui.custom.Glide4Engine;
import com.yc.wsjt.ui.custom.SettingRoleDialog;
import com.yc.wsjt.ui.custom.VideoTimeDialog;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhangdinghui on 2019/5/28.
 */
public class ChatVideoSetActivity extends BaseActivity implements VideoTimeDialog.DateSelectListener {

    private static final int REQUEST_CODE_CHOOSE = 1000;

    @BindView(R.id.tv_title)
    TextView mTitleTv;

    @BindView(R.id.btn_config)
    Button mConfigBtn;

    @BindView(R.id.tv_wait_video)
    TextView mWaitVideoTv;

    @BindView(R.id.tv_video_ing)
    TextView mVideoIngTv;

    @BindView(R.id.layout_wait_video)
    LinearLayout mWaitVideoLayout;

    @BindView(R.id.layout_video_ing)
    LinearLayout mVideoIngLayout;

    @BindView(R.id.tv_target_user_name)
    TextView mTargetUserNameTv;

    @BindView(R.id.iv_target_user_head)
    ImageView mTargetUserBgIv;

    @BindView(R.id.iv_video_bg)
    ImageView mVideoBgIv;

    @BindView(R.id.iv_other_side_video_bg)
    ImageView mOtherSideVideoBgIv;

    @BindView(R.id.iv_my_video_bg)
    ImageView mMyVideoBgIv;

    @BindView(R.id.tv_connection_time)
    TextView mConnectionTimeTv;

    SettingRoleDialog settingRoleDialog;

    private File outputImage;

    VideoTimeDialog videoTimeDialog;

    private int chooseType = 1;

    private String waitMyVideoPath;

    private String videoIngOtherPath;

    private String videoIngMyPath;

    private boolean isUse;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_video_setting;
    }

    @Override
    protected Presenter getPresenter() {
        return null;
    }

    @Override
    protected void initVars() {
        mTitleTv.setText("微信视频设置");
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
        Bundle bundle = getIntent().getExtras();
        isUse = bundle.getBoolean("is_use", false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (App.getApp().getTempPerson() != null) {
            Glide.with(this).load(App.getApp().getTempPerson().mHead).into(mTargetUserBgIv);
            mTargetUserNameTv.setText(App.getApp().getTempPerson().mName);
        }
    }

    @OnClick(R.id.tv_wait_video)
    void waitVideo() {
        mWaitVideoTv.setBackgroundResource(R.drawable.choose_type_selected);
        mWaitVideoTv.setTextColor(ContextCompat.getColor(this, R.color.white));

        mVideoIngTv.setBackgroundResource(R.drawable.choose_type_normal);
        mVideoIngTv.setTextColor(ContextCompat.getColor(this, R.color.black));
        mWaitVideoLayout.setVisibility(View.VISIBLE);
        mVideoIngLayout.setVisibility(View.GONE);
    }

    @OnClick(R.id.tv_video_ing)
    void videoing() {
        mWaitVideoTv.setBackgroundResource(R.drawable.choose_type_normal);
        mWaitVideoTv.setTextColor(ContextCompat.getColor(this, R.color.black));

        mVideoIngTv.setBackgroundResource(R.drawable.choose_type_selected);
        mVideoIngTv.setTextColor(ContextCompat.getColor(this, R.color.white));
        mWaitVideoLayout.setVisibility(View.GONE);
        mVideoIngLayout.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.layout_target)
    void targetUserInfo() {
        if (settingRoleDialog != null && !settingRoleDialog.isShowing()) {
            settingRoleDialog.setType(2);
            settingRoleDialog.show();
        }
    }

    //待接听-视频画面
    @OnClick(R.id.layout_video_bg)
    void waitVideoBg() {
        chooseType = 1;
        Matisse.from(this)
                .choose(MimeType.ofImage())
                .countable(true)
                .maxSelectable(1)
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(new Glide4Engine())
                .showSingleMediaType(true)
                .forResult(REQUEST_CODE_CHOOSE);
    }

    //通话中-对方视频画面
    @OnClick(R.id.layout_other_side_video)
    void otherSideVideoBg() {
        chooseType = 2;
        Matisse.from(this)
                .choose(MimeType.ofImage())
                .countable(true)
                .maxSelectable(1)
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(new Glide4Engine())
                .showSingleMediaType(true)
                .forResult(REQUEST_CODE_CHOOSE);
    }

    //通话中-我的视频画面
    @OnClick(R.id.layout_my_video)
    void myVideoBg() {
        chooseType = 3;
        Matisse.from(this)
                .choose(MimeType.ofImage())
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
                            waitMyVideoPath = Matisse.obtainPathResult(data).get(0);
                            Glide.with(ChatVideoSetActivity.this).load(waitMyVideoPath).into(mVideoBgIv);
                        }
                        if (chooseType == 2) {
                            videoIngOtherPath = Matisse.obtainPathResult(data).get(0);
                            Glide.with(ChatVideoSetActivity.this).load(videoIngOtherPath).into(mOtherSideVideoBgIv);
                        }
                        if (chooseType == 3) {
                            videoIngMyPath = Matisse.obtainPathResult(data).get(0);
                            Glide.with(ChatVideoSetActivity.this).load(videoIngMyPath).into(mMyVideoBgIv);
                        }
                    }
                    break;
            }
        }
    }

    @OnClick(R.id.layout_video_time)
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
        if (mWaitVideoLayout.getVisibility() == View.VISIBLE) {
            if (App.getApp().getTempPerson() == null) {
                ToastUtils.showLong("请选择通话对象");
                return;
            }

            Intent intent = new Intent(this, ChatVideoPreActivity.class);
            intent.putExtra("target_user_name", mTargetUserNameTv.getText().toString());
            intent.putExtra("target_user_head", App.getApp().getTempPerson().mHead);
            intent.putExtra("video_bg", waitMyVideoPath);
            intent.putExtra("is_use", isUse);
            startActivity(intent);
        } else {
            if (mConnectionTimeTv.getText().equals("00:00")) {
                ToastUtils.showLong("请设置通话时间");
                return;
            }
            Intent intent = new Intent(this, ChatVideoingPreActivity.class);
            intent.putExtra("is_use", isUse);
            intent.putExtra("other_side_bg", videoIngOtherPath);
            intent.putExtra("my_video_bg", videoIngMyPath);
            intent.putExtra("video_time", mConnectionTimeTv.getText().toString());
            startActivity(intent);
        }
    }

    @OnClick(R.id.iv_back)
    void back() {
        finish();
    }
}
