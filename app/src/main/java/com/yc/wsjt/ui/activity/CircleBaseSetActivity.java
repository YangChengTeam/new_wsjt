package com.yc.wsjt.ui.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jaeger.library.StatusBarUtil;
import com.orhanobut.logger.Logger;
import com.yc.wsjt.R;
import com.yc.wsjt.presenter.Presenter;
import com.yc.wsjt.ui.custom.Glide4Engine;
import com.yc.wsjt.ui.custom.SettingRoleDialog;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhangdinghui on 2019/5/9.
 */
public class CircleBaseSetActivity extends BaseActivity {

    private static final int REQUEST_CODE_CHOOSE = 1000;

    @BindView(R.id.tv_title)
    TextView mTitleTv;

    @BindView(R.id.btn_config)
    Button mConfigBtn;

    @BindView(R.id.tv_role_name)
    TextView mRoleNameTv;

    @BindView(R.id.iv_role_head)
    ImageView mRoleHeadIv;

    @BindView(R.id.tv_unread_user_name)
    TextView mUnReadUserNameTv;

    @BindView(R.id.iv_unread_user_head)
    ImageView mUnreadUserHeadIv;

    @BindView(R.id.iv_bg_img)
    ImageView mCircleBgIv;

    @BindView(R.id.tv_unread_count)
    TextView mUnreadCountTv;

    SettingRoleDialog settingRoleDialog;

    private File outputImage;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_circle_base_set;
    }

    @Override
    protected Presenter getPresenter() {
        return null;
    }

    @Override
    protected void initVars() {
        StatusBarUtil.setLightMode(this);
        mTitleTv.setText(getResources().getString(R.string.setting_ziliao));
        mConfigBtn.setVisibility(View.GONE);
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
        try {

        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.layout_role)
    void chooseRole() {
        if (settingRoleDialog != null && !settingRoleDialog.isShowing()) {
            settingRoleDialog.setType(0);
            settingRoleDialog.show();
        }
    }

    @OnClick(R.id.layout_unread_user_info)
    void chooseUnRead() {
        if (settingRoleDialog != null && !settingRoleDialog.isShowing()) {
            settingRoleDialog.setType(1);
            settingRoleDialog.show();
        }
    }

    @OnClick(R.id.layout_bg_image)
    void chooseImage() {
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
                    if (Matisse.obtainPathResult(data) != null && Matisse.obtainPathResult(data).size() > 0) {
                        String tempPath = Matisse.obtainPathResult(data).get(0);
                        Logger.i("out path--->" + tempPath);
                        Glide.with(CircleBaseSetActivity.this).load(tempPath).into(mCircleBgIv);

                    }
                    break;
            }
        }
    }
}
