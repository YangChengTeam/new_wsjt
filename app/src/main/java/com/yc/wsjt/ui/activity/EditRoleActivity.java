package com.yc.wsjt.ui.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jaeger.library.StatusBarUtil;
import com.yc.wsjt.App;
import com.yc.wsjt.R;
import com.yc.wsjt.presenter.Presenter;
import com.yc.wsjt.ui.custom.Glide4Engine;
import com.yc.wsjt.ui.custom.InputDialog;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhangdinghui on 2019/6/5.
 */
public class EditRoleActivity extends BaseActivity implements InputDialog.InputTxtListener {

    private static final int REQUEST_CODE_CHOOSE = 1000;

    @BindView(R.id.iv_back)
    ImageView mBackIv;

    @BindView(R.id.tv_title)
    TextView mTitleTv;

    @BindView(R.id.tv_setting)
    TextView mSettingTv;

    @BindView(R.id.iv_user_head)
    ImageView mUserHeadIv;

    @BindView(R.id.tv_nick_name)
    TextView mNickNameTv;

    InputDialog inputDialog;

    private String resultHeadUrl;

    private int roleType;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_role;
    }

    @Override
    protected Presenter getPresenter() {
        return null;
    }

    @Override
    protected void initVars() {
        StatusBarUtil.setLightMode(this);
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.white), 0);
        mTitleTv.setText("编辑角色");
        mSettingTv.setText("确定");
    }

    @Override
    protected void initViews() {
        inputDialog = new InputDialog(this, R.style.scale_dialog, "设置昵称");
        inputDialog.setTxtListener(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            roleType = bundle.getInt("role_type", 0);
            mNickNameTv.setText(roleType == 0 ? App.getApp().chatDataInfo.personName : App.getApp().chatDataInfo.otherPersonName);

            resultHeadUrl = roleType == 0 ? App.getApp().chatDataInfo.personHead : App.getApp().chatDataInfo.otherPersonHead;

            RequestOptions options = new RequestOptions();
            options.error(R.mipmap.user_head_def);
            Glide.with(this).load(resultHeadUrl).apply(options).into(mUserHeadIv);
        }
    }

    @OnClick(R.id.iv_user_head)
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
                        resultHeadUrl = Matisse.obtainPathResult(data).get(0);
                        Glide.with(EditRoleActivity.this).load(resultHeadUrl).into(mUserHeadIv);
                    }
                    break;
            }
        }
    }

    @OnClick(R.id.layout_nick_name)
    void updateName() {
        inputDialog.show();
        WindowManager.LayoutParams windowParams = inputDialog.getWindow().getAttributes();
        windowParams.width = (int) (ScreenUtils.getScreenWidth() * 0.75);
        windowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        inputDialog.getWindow().setAttributes(windowParams);
    }

    @Override
    public void inputTxt(String inputTxt) {
        mNickNameTv.setText(inputTxt);
    }

    @OnClick(R.id.tv_setting)
    void config() {
        if (App.getApp().chatDataInfo == null) {
            ToastUtils.showLong("资料信息错误，请重试");
            return;
        }
        if (roleType == 0) {
            mAppDatabase.chatDataInfoDao().updateSelf(mNickNameTv.getText().toString(), resultHeadUrl, App.getApp().chatDataInfo.getId());
        } else {
            mAppDatabase.chatDataInfoDao().updateOther(mNickNameTv.getText().toString(), resultHeadUrl, App.getApp().chatDataInfo.getId());
        }

        finish();
    }
}
