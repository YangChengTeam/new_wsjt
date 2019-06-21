package com.yc.wsjt.ui.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.jaeger.library.StatusBarUtil;
import com.yc.wsjt.R;
import com.yc.wsjt.presenter.Presenter;

import butterknife.BindView;

/**
 * Created by zhangdinghui on 2019/5/29.
 */
public class PaySuccessTransferActivity extends BaseActivity {

    @BindView(R.id.tv_receive_user_name)
    TextView mReceiveUserNameTv;

    @BindView(R.id.tv_pay_money)
    TextView mPayMoneyTv;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pay_success_transfer;
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
            mReceiveUserNameTv.setText("待" + bundle.getString("wx_name") + "确认收钱");
            mPayMoneyTv.setText(bundle.getString("wx_money"));
        }
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }
}
