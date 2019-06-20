package com.yc.wsjt.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.jaeger.library.StatusBarUtil;
import com.yc.wsjt.R;
import com.yc.wsjt.presenter.Presenter;

import java.text.DecimalFormat;

import butterknife.BindView;

public class ExtractPreActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView mTitleTv;

    @BindView(R.id.tv_receive_date)
    TextView mReceiveDateTv;

    @BindView(R.id.tv_extract_money)
    TextView mMoneyTv;

    @BindView(R.id.tv_extract_charge)
    TextView mMoneyChargeTv;

    @BindView(R.id.tv_bank_info)
    TextView mBankInfoTv;

    @BindView(R.id.layout_server_charge)
    RelativeLayout mServerChargeLayout;

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
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mReceiveDateTv.setText("预计" + bundle.getString("receive_date") + "到账");
            mMoneyTv.setText("¥" + bundle.getString("money"));
            if (bundle.getBoolean("show_charge", false)) {
                mServerChargeLayout.setVisibility(View.VISIBLE);
                double extractMOney = Double.parseDouble(bundle.getString("money"));
                DecimalFormat df = new DecimalFormat(".00");
                String temp = df.format(extractMOney * 0.01);
                mMoneyChargeTv.setText("¥" + temp);
            } else {
                mServerChargeLayout.setVisibility(View.GONE);
            }

            mBankInfoTv.setText(bundle.getString("bank_name") + " 尾号" + bundle.getString("bank_last_number"));
        }
    }

}
