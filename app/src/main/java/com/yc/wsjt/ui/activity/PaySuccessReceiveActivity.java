package com.yc.wsjt.ui.activity;

import android.os.Bundle;

import androidx.core.content.ContextCompat;

import com.jaeger.library.StatusBarUtil;
import com.yc.wsjt.R;
import com.yc.wsjt.presenter.Presenter;

/**
 * Created by zhangdinghui on 2019/5/29.
 */
public class PaySuccessReceiveActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pay_success_receive;
    }

    @Override
    protected Presenter getPresenter() {
        return null;
    }

    @Override
    protected void initVars() {
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.white), 0);
        StatusBarUtil.setLightMode(this);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }
}
