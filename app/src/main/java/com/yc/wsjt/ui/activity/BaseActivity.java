package com.yc.wsjt.ui.activity;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.jaeger.library.StatusBarUtil;
import com.yc.wsjt.App;
import com.yc.wsjt.R;
import com.yc.wsjt.common.AppDatabase;
import com.yc.wsjt.presenter.Presenter;
import com.yc.wsjt.ui.custom.OpenVipDialog;
import com.yc.wsjt.ui.custom.VipPayTypeDialog;

import butterknife.ButterKnife;

/**
 * Created by admin on 2017/4/8.
 */

public abstract class BaseActivity extends AppCompatActivity implements OpenVipDialog.VipListener{

    public Context context;
    public Presenter presenter;
    public AppDatabase mAppDatabase;

    OpenVipDialog openVipDialog;

    VipPayTypeDialog vipPayTypeDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        setStatusBar();
        context = this;
        ButterKnife.bind(this);
        mAppDatabase = ((App) getApplication()).getAppDatabase();
        openVipDialog = new OpenVipDialog(this, R.style.custom_dialog);
        openVipDialog.setVipListener(this);
        vipPayTypeDialog = new VipPayTypeDialog(this, R.style.custom_dialog);
        initVars();
        initViews();
        initData(savedInstanceState);
    }

    public void setStatusBar() {
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.colorPrimary), 0);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (presenter == null && getPresenter() != null) {
            presenter = getPresenter();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (presenter != null) {
            presenter.destroy();
        }
    }

    public Context getContext() {
        return context;
    }

    protected abstract int getLayoutId();

    protected abstract Presenter getPresenter();

    protected abstract void initVars();

    protected abstract void initViews();

    protected abstract void initData(Bundle savedInstanceState);

    @Override
    public void addComment() {
    }

    @Override
    public void openVip() {
        if (vipPayTypeDialog != null && !vipPayTypeDialog.isShowing()) {
            vipPayTypeDialog.show();
        }
    }

    @Override
    public void closeOpenVip() {

    }

}
