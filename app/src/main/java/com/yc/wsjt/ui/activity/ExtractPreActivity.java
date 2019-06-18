package com.yc.wsjt.ui.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.jaeger.library.StatusBarUtil;
import com.yc.wsjt.R;
import com.yc.wsjt.presenter.Presenter;

import butterknife.BindView;

public class ExtractPreActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView mTitleTv;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_extract_show;
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
        mTitleTv.setText("零钱提现");
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.white), 0);
        StatusBarUtil.setLightMode(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

}
