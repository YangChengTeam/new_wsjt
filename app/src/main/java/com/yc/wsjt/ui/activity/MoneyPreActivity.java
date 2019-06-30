package com.yc.wsjt.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.yc.wsjt.R;
import com.yc.wsjt.presenter.Presenter;

import butterknife.BindView;
import butterknife.OnClick;

public class MoneyPreActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView mBackIv;

    @BindView(R.id.tv_money)
    TextView mMoneyTv;

    @BindView(R.id.tv_profit_remark)
    TextView mProfitRemarkTv;

    private boolean isUse;

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
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mMoneyTv.setText(bundle.getString("money"));
            if (bundle.getBoolean("show_profit", false)) {
                mProfitRemarkTv.setText(bundle.getString("profit_remark"));
                mProfitRemarkTv.setVisibility(View.VISIBLE);
            }
            isUse = bundle.getBoolean("is_use", false);
        }
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        if (!isUse) {
            if (openVipDialog != null && !openVipDialog.isShowing()) {
                openVipDialog.show();
                return;
            }
        }
    }

    @Override
    public void addComment() {
        super.addComment();
    }

    @Override
    public void closeOpenVip() {
        super.closeOpenVip();
        finish();
    }

    @OnClick(R.id.iv_back)
    void back() {
        finish();
    }
}
