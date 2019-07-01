package com.yc.wsjt.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.jaeger.library.StatusBarUtil;
import com.orhanobut.logger.Logger;
import com.yc.wsjt.R;
import com.yc.wsjt.presenter.Presenter;
import com.yc.wsjt.ui.custom.VipPayTypeDialog;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.OnClick;

public class ExtractPreActivity extends BaseActivity implements VipPayTypeDialog.PayListener {

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

    private boolean isUse;

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
        vipPayTypeDialog.setPayListener(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            isUse = bundle.getBoolean("is_use", false);
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

    @Override
    public void pay() {
        Logger.i("打开支付--->");
    }

    @Override
    public void payClose() {
        Logger.i("支付界面关闭--->");
        finish();
    }

    @OnClick(R.id.iv_back)
    void back() {
        finish();
    }
}
