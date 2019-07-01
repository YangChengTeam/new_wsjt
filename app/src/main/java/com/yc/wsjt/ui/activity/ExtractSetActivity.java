package com.yc.wsjt.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jaeger.library.StatusBarUtil;
import com.kyleduo.switchbutton.SwitchButton;
import com.orhanobut.logger.Logger;
import com.yc.wsjt.R;
import com.yc.wsjt.presenter.Presenter;
import com.yc.wsjt.ui.custom.BankListDialog;
import com.yc.wsjt.ui.custom.CustomDateDialog;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.OnClick;

public class ExtractSetActivity extends BaseActivity implements CustomDateDialog.DateSelectListener, BankListDialog.BankInfoListener {

    @BindView(R.id.tv_title)
    TextView mTitleTv;

    @BindView(R.id.btn_config)
    Button mConfigBtn;

    @BindView(R.id.tv_receive_time)
    TextView mReceiveTimeTv;

    @BindView(R.id.tv_bank)
    TextView mBankNameTv;

    @BindView(R.id.et_bank_number)
    EditText mBankNumberEt;

    @BindView(R.id.et_money)
    EditText mMoneyEt;

    @BindView(R.id.sb_server_charge)
    SwitchButton mChargeBtn;

    CustomDateDialog customDateDialog;

    BankListDialog bankListDialog;

    WindowManager.LayoutParams windowParams;

    private boolean isUse;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_extract_set;
    }

    @Override
    protected Presenter getPresenter() {
        return null;
    }

    @Override
    protected void initVars() {
        StatusBarUtil.setLightMode(this);

        customDateDialog = new CustomDateDialog(this, R.style.date_dialog);
        customDateDialog.setDateSelectListener(this);
        bankListDialog = new BankListDialog(this, R.style.custom_dialog);
        bankListDialog.setBankInfoListener(this);
    }

    @Override
    protected void initViews() {
        mConfigBtn.setVisibility(View.GONE);
        mTitleTv.setText("零钱提现");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            isUse = bundle.getBoolean("is_use", true);
        }

        mReceiveTimeTv.setText(TimeUtils.getNowString());
    }

    @OnClick(R.id.layout_receive_time)
    void receiveDate() {
        customDateDialog.show();

        //设置Dialog从窗体底部弹出
        customDateDialog.getWindow().setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams windowParams = customDateDialog.getWindow().getAttributes();
        windowParams.width = ScreenUtils.getScreenWidth();
        windowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        customDateDialog.getWindow().setAttributes(windowParams);
    }

    @OnClick(R.id.layout_bank)
    void chooseBank() {
        if (bankListDialog != null && !bankListDialog.isShowing()) {
            bankListDialog.show();
            bankListDialog.setCanceledOnTouchOutside(true);
            windowParams = bankListDialog.getWindow().getAttributes();
            windowParams.width = (int) (ScreenUtils.getScreenWidth() * 0.92);
            windowParams.height = (int) (ScreenUtils.getScreenHeight() * 0.6);
            bankListDialog.getWindow().setAttributes(windowParams);
        }
    }

    @Override
    public void configDate(String selectDate) {
        Logger.i("select date --->" + selectDate);
        mReceiveTimeTv.setText(selectDate);
    }

    @OnClick(R.id.btn_show_pre)
    void preShow() {
        if (StringUtils.isEmpty(mBankNameTv.getText())) {
            ToastUtils.showLong("请选择银行");
            return;
        }

        if (StringUtils.isEmpty(mBankNumberEt.getText())) {
            ToastUtils.showLong("请输入银行卡后4位");
            return;
        }

        if (StringUtils.isEmpty(mMoneyEt.getText())) {
            ToastUtils.showLong("请输入提现金额");
            return;
        }

        Intent intent = new Intent(this, ExtractPreActivity.class);
        intent.putExtra("receive_date", mReceiveTimeTv.getText());
        intent.putExtra("show_charge", mChargeBtn.isChecked());
        intent.putExtra("bank_name", mBankNameTv.getText());
        intent.putExtra("bank_last_number", mBankNumberEt.getText().toString() + "");
        DecimalFormat df = new DecimalFormat(".00");
        String temp = df.format(Double.parseDouble(mMoneyEt.getText().toString()));
        intent.putExtra("money", temp);
        intent.putExtra("is_use", isUse);
        startActivity(intent);
    }

    @Override
    public void chooseBank(String bankName) {
        mBankNameTv.setText(bankName);
    }
}
