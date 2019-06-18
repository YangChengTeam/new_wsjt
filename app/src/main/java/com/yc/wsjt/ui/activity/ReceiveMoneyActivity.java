package com.yc.wsjt.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.jaeger.library.StatusBarUtil;
import com.yc.wsjt.R;
import com.yc.wsjt.presenter.Presenter;

import butterknife.BindView;
import butterknife.OnClick;

public class ReceiveMoneyActivity extends BaseActivity {

    private int receiveType = 1;

    @BindView(R.id.iv_back)
    ImageView mBackIv;

    @BindView(R.id.tv_look_money)
    TextView mLookMoneyTv;

    @BindView(R.id.layout_profit)
    LinearLayout mProfitLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_receive_money;
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
        Bundle bundle = getIntent().getExtras();
        if (bundle.getInt("receive_type") > 0) {
            receiveType = bundle.getInt("receive_type");
        }
        if (receiveType == 1) {
            mLookMoneyTv.setText("查看零钱");
            mLookMoneyTv.setTextColor(ContextCompat.getColor(this, R.color.look_money_color));
            mProfitLayout.setVisibility(View.VISIBLE);
        } else {
            mLookMoneyTv.setText("已存入对方零钱中");
            mLookMoneyTv.setTextColor(ContextCompat.getColor(this, R.color.transfer_color));
            mProfitLayout.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @OnClick(R.id.iv_back)
    void back() {
        finish();
    }
}
