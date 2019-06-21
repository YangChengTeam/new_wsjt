package com.yc.wsjt.ui.activity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.PayInfo;
import com.yc.wsjt.presenter.Presenter;
import com.yc.wsjt.ui.custom.CustomDateDialog;
import com.yc.wsjt.ui.custom.NoticeDateDialog;
import com.yc.wsjt.ui.custom.PayDateDialog;
import com.yc.wsjt.util.DataUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhangdinghui on 2019/5/29.
 */
public class WxPayEndActivity extends BaseActivity implements NoticeDateDialog.NoticeDateSelectListener, PayDateDialog.PayDateSelectListener, CustomDateDialog.DateSelectListener {

    @BindView(R.id.iv_back)
    ImageView mBackIv;

    @BindView(R.id.tv_title)
    TextView mTitleTv;

    @BindView(R.id.et_notice_time)
    EditText mNoticeDateEt;

    @BindView(R.id.et_pay_time)
    EditText mPayDateEt;

    @BindView(R.id.et_pay_money)
    EditText mPayMoneyEt;

    @BindView(R.id.et_bank)
    EditText mBankEt;

    @BindView(R.id.et_get_time)
    EditText mGetDateEt;

    @BindView(R.id.et_arrival_time)
    EditText mArrivalDateEt;

    NoticeDateDialog noticeDateDialog;

    PayDateDialog payDateDialog;

    CustomDateDialog customDateDialog;

    private int chooseType = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_wx_pay_end;
    }

    @Override
    protected Presenter getPresenter() {
        return null;
    }

    @Override
    protected void initVars() {
        noticeDateDialog = new NoticeDateDialog(this, R.style.date_dialog);
        noticeDateDialog.setNoticeDateSelectListener(this);

        payDateDialog = new PayDateDialog(this, R.style.date_dialog);
        payDateDialog.setPayDateSelectListener(this);

        customDateDialog = new CustomDateDialog(this, R.style.date_dialog);
        customDateDialog.setDateSelectListener(this);

    }

    @Override
    protected void initViews() {
        mTitleTv.setText("零钱提现发起");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @OnClick(R.id.iv_notice_date)
    void noticeDate() {
        noticeDateDialog.show();

        //设置Dialog从窗体底部弹出
        noticeDateDialog.getWindow().setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams windowParams = noticeDateDialog.getWindow().getAttributes();
        windowParams.width = ScreenUtils.getScreenWidth();
        windowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        noticeDateDialog.getWindow().setAttributes(windowParams);
    }

    @OnClick(R.id.iv_pay_date)
    void payDate() {
        payDateDialog.show();

        //设置Dialog从窗体底部弹出
        payDateDialog.getWindow().setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams windowParams = payDateDialog.getWindow().getAttributes();
        windowParams.width = ScreenUtils.getScreenWidth();
        windowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        payDateDialog.getWindow().setAttributes(windowParams);
    }

    void showCustomDate() {
        customDateDialog.show();
        //设置Dialog从窗体底部弹出
        customDateDialog.getWindow().setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams windowParams = customDateDialog.getWindow().getAttributes();
        windowParams.width = ScreenUtils.getScreenWidth();
        windowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        customDateDialog.getWindow().setAttributes(windowParams);
    }

    @OnClick(R.id.iv_get_date)
    void getDate() {
        chooseType = 1;
        showCustomDate();
    }

    @OnClick(R.id.iv_arrival_date)
    void arrivalDate() {
        chooseType = 2;
        showCustomDate();
    }

    @Override
    public void noticeConfigDate(String selectDate) {
        mNoticeDateEt.setText(selectDate);
    }

    @OnClick(R.id.btn_config)
    void config() {

        if (StringUtils.isEmpty(mNoticeDateEt.getText())) {
            ToastUtils.showLong("请输入通知时间");
            return;
        }
        if (StringUtils.isEmpty(mNoticeDateEt.getText())) {
            ToastUtils.showLong("请输入支付时间");
            return;
        }

        if (StringUtils.isEmpty(mPayMoneyEt.getText())) {
            ToastUtils.showLong("请填写金额");
            return;
        }

        if (StringUtils.isEmpty(mBankEt.getText())) {
            ToastUtils.showLong("请填写银行名称及后四位");
            return;
        }

        if (StringUtils.isEmpty(mGetDateEt.getText())) {
            ToastUtils.showLong("请填写提现时间");
            return;
        }

        if (StringUtils.isEmpty(mArrivalDateEt.getText())) {
            ToastUtils.showLong("请填写到账时间");
            return;
        }

        PayInfo payInfo = new PayInfo(PayInfo.MONEY_END);
        payInfo.setPayType(5);
        payInfo.setNoticeDate(mNoticeDateEt.getText().toString());
        payInfo.setPayDate(mPayDateEt.getText().toString());
        payInfo.setPayMoney(DataUtils.getMoney(mPayMoneyEt.getText().toString()));
        payInfo.setBankName(mBankEt.getText().toString());
        payInfo.setGetMoneyDate(mGetDateEt.getText().toString());
        payInfo.setReceiveMoneyDate(mArrivalDateEt.getText().toString());
        mAppDatabase.payInfoDao().insert(payInfo);

        finish();
    }

    @OnClick(R.id.iv_back)
    void back() {
        finish();
    }

    @Override
    public void payDate(String payDate) {
        mPayDateEt.setText(payDate);
    }

    @Override
    public void configDate(String selectDate) {
        if (chooseType == 1) {
            mGetDateEt.setText(selectDate);
        } else {
            mArrivalDateEt.setText(selectDate);
        }

    }
}
