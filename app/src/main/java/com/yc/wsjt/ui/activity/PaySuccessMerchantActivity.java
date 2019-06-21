package com.yc.wsjt.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.jaeger.library.StatusBarUtil;
import com.yc.wsjt.R;
import com.yc.wsjt.presenter.Presenter;

import butterknife.BindView;

/**
 * Created by zhangdinghui on 2019/5/29.
 */
public class PaySuccessMerchantActivity extends BaseActivity {

    @BindView(R.id.tv_merchant_name)
    TextView mMerchantNameTv;

    @BindView(R.id.tv_pay_money)
    TextView mPayMoneyTv;

    @BindView(R.id.layout_follow)
    LinearLayout mFollowLayout;

    @BindView(R.id.tv_follow_merchant_name)
    TextView mFollowMerchantNameTv;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pay_success_merchant;
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
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mPayMoneyTv.setText(bundle.getString("wx_money"));
            mFollowLayout.setVisibility(bundle.getBoolean("follow_merchant", false) ? View.VISIBLE : View.GONE);
            mMerchantNameTv.setText(bundle.getString("wx_merchant_name"));
            mFollowMerchantNameTv.setText(bundle.getString("wx_merchant_name"));
        }
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }
}
