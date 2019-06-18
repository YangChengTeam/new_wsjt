package com.yc.wsjt.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;
import com.jaeger.library.StatusBarUtil;
import com.orhanobut.logger.Logger;
import com.yc.wsjt.R;
import com.yc.wsjt.presenter.Presenter;
import com.yc.wsjt.ui.custom.BankListDialog;
import com.yc.wsjt.ui.custom.CustomDateDialog;

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

    CustomDateDialog customDateDialog;

    BankListDialog bankListDialog;

    WindowManager.LayoutParams windowParams;

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
        mTitleTv.setText("提现流程");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

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
        Intent intent = new Intent(this, ExtractPreActivity.class);
        startActivity(intent);
    }

    @Override
    public void chooseBank(String bankName) {
        mBankNameTv.setText(bankName);
    }
}
