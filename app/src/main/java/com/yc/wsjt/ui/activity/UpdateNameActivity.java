package com.yc.wsjt.ui.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jaeger.library.StatusBarUtil;
import com.yc.wsjt.App;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.UserInfoRet;
import com.yc.wsjt.common.Constants;
import com.yc.wsjt.presenter.Presenter;
import com.yc.wsjt.presenter.UserInfoPresenterImp;
import com.yc.wsjt.view.UserInfoView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhangdinghui on 2019/6/5.
 */
public class UpdateNameActivity extends BaseActivity implements UserInfoView {

    @BindView(R.id.iv_back)
    ImageView mBackIv;

    @BindView(R.id.tv_title)
    TextView mTitleTv;

    @BindView(R.id.et_user_name)
    EditText mUserNameEt;

    @BindView(R.id.btn_pre_show)
    Button mConfigBtn;

    UserInfoPresenterImp userInfoPresenterImp;

    private ProgressDialog progressDialog = null;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_update_name;
    }

    @Override
    protected Presenter getPresenter() {
        return null;
    }

    @Override
    protected void initVars() {
        StatusBarUtil.setLightMode(this);
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.white), 0);
        mTitleTv.setText("修改用户名");
    }

    @Override
    protected void initViews() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("正在修改");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        userInfoPresenterImp = new UserInfoPresenterImp(this, this);
    }

    @OnClick(R.id.btn_pre_show)
    void updateName() {
        if (App.getApp().mUserInfo == null) {
            ToastUtils.showLong("用户未登录");
        }

        if (StringUtils.isEmpty(mUserNameEt.getText())) {
            ToastUtils.showLong("请输入用户名");
            return;
        }
        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.show();
        }
        App.getApp().mUserInfo.setNickName(mUserNameEt.getText().toString());
        userInfoPresenterImp.updateName(App.getApp().mUserInfo);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void dismissProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void loadDataSuccess(UserInfoRet tData) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        if (tData != null && tData.getCode() == Constants.SUCCESS) {
            ToastUtils.showLong("修改成功");
            finish();
        }
    }

    @Override
    public void loadDataError(Throwable throwable) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        ToastUtils.showLong("修改失败");
    }
}
