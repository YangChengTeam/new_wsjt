package com.yc.wsjt.ui.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.jaeger.library.StatusBarUtil;
import com.orhanobut.logger.Logger;
import com.yc.wsjt.R;
import com.yc.wsjt.presenter.Presenter;
import com.yc.wsjt.ui.custom.VipPayTypeDialog;

import butterknife.BindView;

/**
 * Created by zhangdinghui on 2019/5/29.
 */
public class PaySuccessTransferActivity extends BaseActivity implements VipPayTypeDialog.PayListener {

    @BindView(R.id.tv_receive_user_name)
    TextView mReceiveUserNameTv;

    @BindView(R.id.tv_pay_money)
    TextView mPayMoneyTv;

    private boolean isUse;

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
        vipPayTypeDialog.setPayListener(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            isUse = bundle.getBoolean("is_use", false);
            mReceiveUserNameTv.setText("待" + bundle.getString("wx_name") + "确认收钱");
            mPayMoneyTv.setText(bundle.getString("wx_money"));
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

    @Override
    public void pay() {
        Logger.i("打开支付--->");
    }

    @Override
    public void payClose() {
        Logger.i("支付界面关闭--->");
        finish();
    }

}
