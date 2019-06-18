package com.yc.wsjt.ui.activity;

import android.os.Bundle;
import android.widget.ImageView;

import com.jaeger.library.StatusBarUtil;
import com.yc.wsjt.R;
import com.yc.wsjt.presenter.Presenter;

import butterknife.BindView;
import butterknife.OnClick;

public class MoneyPreActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView mBackIv;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_money_pre_show;
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

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @OnClick(R.id.iv_back)
    void back() {
        finish();
    }
}
