package com.yc.wsjt.ui.activity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jaeger.library.StatusBarUtil;
import com.orhanobut.logger.Logger;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.MoneyDetail;
import com.yc.wsjt.presenter.Presenter;
import com.yc.wsjt.ui.custom.CustomDateDialog;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhangdinghui on 2019/5/27.
 */
public class AddMoneyDetailActivity extends BaseActivity implements CustomDateDialog.DateSelectListener {

    @BindView(R.id.iv_back)
    ImageView mBackIv;

    @BindView(R.id.tv_title)
    TextView mTitleTv;

    @BindView(R.id.tv_receive_money)
    TextView mReceiveMoneyTv;

    @BindView(R.id.tv_send_money)
    TextView mSendMoneyTv;

    @BindView(R.id.layout_transaction)
    RelativeLayout mTransactionLayout;

    @BindView(R.id.tv_transaction_time)
    TextView mTransactionTimeTv;

    @BindView(R.id.et_transaction_type)
    EditText mTransactionTypeTv;

    @BindView(R.id.et_money)
    EditText mMoneyEt;

    @BindView(R.id.iv_refresh)
    ImageView mRefreshIv;

    CustomDateDialog customDateDialog;

    private String[] types = {"微信面对面收钱", "退款", "微信转账", "微信红包", "群收款", "充值"};

    private int typeIndex;

    private int moneyType = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_money_detail;
    }

    @Override
    protected Presenter getPresenter() {
        return null;
    }

    @Override
    protected void initVars() {
        mTitleTv.setText("添加记录");
        mTransactionTimeTv.setText(TimeUtils.getNowString());
        customDateDialog = new CustomDateDialog(this, R.style.date_dialog);
        customDateDialog.setDateSelectListener(this);
    }

    @Override
    protected void initViews() {
        StatusBarUtil.setLightMode(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mTransactionTypeTv.setText(types[typeIndex]);
    }

    @OnClick(R.id.tv_receive_money)
    void sendTransfer() {
        moneyType = 1;
        mReceiveMoneyTv.setBackgroundResource(R.drawable.choose_type_selected);
        mReceiveMoneyTv.setTextColor(ContextCompat.getColor(this, R.color.white));

        mSendMoneyTv.setBackgroundResource(R.drawable.choose_type_normal);
        mSendMoneyTv.setTextColor(ContextCompat.getColor(this, R.color.black));
    }

    @OnClick(R.id.tv_send_money)
    void receiveTransfer() {
        moneyType = 2;
        mReceiveMoneyTv.setBackgroundResource(R.drawable.choose_type_normal);
        mReceiveMoneyTv.setTextColor(ContextCompat.getColor(this, R.color.black));

        mSendMoneyTv.setBackgroundResource(R.drawable.choose_type_selected);
        mSendMoneyTv.setTextColor(ContextCompat.getColor(this, R.color.white));
    }

    @OnClick(R.id.iv_refresh)
    void refreshType() {
        typeIndex++;
        if (typeIndex >= types.length) {
            typeIndex = 0;
        }
        mTransactionTypeTv.setText(types[typeIndex]);
    }

    @OnClick(R.id.layout_transaction)
    void transactionType() {
        customDateDialog.show();

        //设置Dialog从窗体底部弹出
        customDateDialog.getWindow().setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams windowParams = customDateDialog.getWindow().getAttributes();
        windowParams.width = ScreenUtils.getScreenWidth();
        windowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        customDateDialog.getWindow().setAttributes(windowParams);
    }

    @Override
    public void configDate(String selectDate) {
        Logger.i("select date --->" + selectDate);
        mTransactionTimeTv.setText(selectDate);
    }

    @OnClick(R.id.btn_config)
    void configInfo() {

        if (StringUtils.isEmpty(mTransactionTypeTv.getText())) {
            ToastUtils.showLong("请输入零钱明细说明");
            return;
        }

        if (StringUtils.isEmpty(mMoneyEt.getText())) {
            ToastUtils.showLong("请输入金额");
            return;
        }

        MoneyDetail moneyDetail = new MoneyDetail();
        moneyDetail.setAddTime(mTransactionTimeTv.getText().toString());
        moneyDetail.setMoneyTitle(mTransactionTypeTv.getText().toString());

        DecimalFormat df = new DecimalFormat(".00");
        String temp = df.format(Double.parseDouble(mMoneyEt.getText().toString()));
        moneyDetail.setMoney(temp);
        moneyDetail.setMoneyType(moneyType);

        mAppDatabase.moneyDetailDao().insert(moneyDetail);

        finish();
    }

}
