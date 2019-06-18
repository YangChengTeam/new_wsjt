package com.yc.wsjt.ui.activity;

import android.os.Bundle;

import com.jaeger.library.StatusBarUtil;
import com.yc.wsjt.R;
import com.yc.wsjt.presenter.Presenter;

/**
 * Created by zhangdinghui on 2019/6/3.
 */
public class WxOtherSetActivity extends BaseActivity{

    @Override
    protected int getLayoutId() {
        return R.layout.activity_wx_other_set;
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
}
