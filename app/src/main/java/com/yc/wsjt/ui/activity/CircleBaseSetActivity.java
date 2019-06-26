package com.yc.wsjt.ui.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.PhoneUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.jaeger.library.StatusBarUtil;
import com.orhanobut.logger.Logger;
import com.yc.wsjt.App;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.CircleBaseSetInfo;
import com.yc.wsjt.presenter.Presenter;
import com.yc.wsjt.ui.custom.Glide4Engine;
import com.yc.wsjt.ui.custom.InputDialog;
import com.yc.wsjt.ui.custom.SettingRoleDialog;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhangdinghui on 2019/5/9.
 */
public class CircleBaseSetActivity extends BaseActivity implements InputDialog.InputTxtListener {

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

    private String circleBgPath;

    private CircleBaseSetInfo circleBaseSetInfo;

    private int chooseUserType = -1;

    InputDialog inputDialog;

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
        inputDialog = new InputDialog(this, R.style.scale_dialog, "未读消息数");
        inputDialog.setTxtListener(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            if (mAppDatabase.circleBaseSetInfoDao().getItemById(PhoneUtils.getDeviceId()) != null) {
                circleBaseSetInfo = mAppDatabase.circleBaseSetInfoDao().getItemById(PhoneUtils.getDeviceId());
                mRoleNameTv.setText(circleBaseSetInfo.getRoleName());
                mUnReadUserNameTv.setText(circleBaseSetInfo.getUnreadUserName());
                mUnreadCountTv.setText(StringUtils.isEmpty(circleBaseSetInfo.getUnreadCount()) ? "0" : circleBaseSetInfo.getUnreadCount());
                Glide.with(this).load(circleBaseSetInfo.getRoleHead()).into(mRoleHeadIv);
                Glide.with(this).load(circleBaseSetInfo.getUnreadUserHead()).into(mUnreadUserHeadIv);
                Glide.with(this).load(circleBaseSetInfo.getCoverImage()).into(mCircleBgIv);
            } else {
                circleBaseSetInfo = new CircleBaseSetInfo();
                circleBaseSetInfo.setDeviceId(PhoneUtils.getDeviceId());
                if (App.getApp().mUserInfo != null) {
                    circleBaseSetInfo.setRoleName(App.getApp().mUserInfo.getNickName());
                    circleBaseSetInfo.setRoleHead(App.getApp().mUserInfo.getAvatar());
                }
                circleBaseSetInfo.setUnreadCount("0");
                Long cid = mAppDatabase.circleBaseSetInfoDao().insert(circleBaseSetInfo);
                circleBaseSetInfo.setId(cid.intValue());
            }

            if (App.getApp().getTempPerson() != null && chooseUserType > -1) {
                if (chooseUserType == 1) {
                    mRoleNameTv.setText(App.getApp().getTempPerson().mName);
                    Glide.with(this).load(App.getApp().getTempPerson().mHead).into(mRoleHeadIv);
                    mAppDatabase.circleBaseSetInfoDao().updateRole(App.getApp().getTempPerson().mName, App.getApp().getTempPerson().mHead, circleBaseSetInfo.getId());
                } else {
                    mUnReadUserNameTv.setText(App.getApp().getTempPerson().mName);
                    Glide.with(this).load(App.getApp().getTempPerson().mHead).into(mUnreadUserHeadIv);
                    mAppDatabase.circleBaseSetInfoDao().updateUnReadUserInfo(App.getApp().getTempPerson().mName, App.getApp().getTempPerson().mHead, circleBaseSetInfo.getId());
                }
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.layout_role)
    void chooseRole() {
        if (settingRoleDialog != null && !settingRoleDialog.isShowing()) {
            chooseUserType = 1;
            settingRoleDialog.setType(2);
            settingRoleDialog.show();
        }
    }

    @OnClick(R.id.layout_unread_user_info)
    void chooseUnRead() {
        if (settingRoleDialog != null && !settingRoleDialog.isShowing()) {
            chooseUserType = 2;
            settingRoleDialog.setType(2);
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
                        circleBgPath = Matisse.obtainPathResult(data).get(0);
                        Logger.i("out path--->" + circleBgPath);
                        Glide.with(CircleBaseSetActivity.this).load(circleBgPath).into(mCircleBgIv);
                        if (circleBaseSetInfo != null) {
                            mAppDatabase.circleBaseSetInfoDao().updateBgImage(circleBgPath, circleBaseSetInfo.getId());
                        }
                    }
                    break;
            }
        }
    }

    @OnClick(R.id.layout_unread_count)
    void unreadCount() {
        inputDialog.show();

        WindowManager.LayoutParams windowParams = inputDialog.getWindow().getAttributes();
        windowParams.width = (int) (ScreenUtils.getScreenWidth() * 0.75);
        windowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        inputDialog.getWindow().setAttributes(windowParams);
    }

    @Override
    public void inputTxt(String number) {
        mUnreadCountTv.setText(number);
        mAppDatabase.circleBaseSetInfoDao().updateUnReadCount(number, circleBaseSetInfo.getId());
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
