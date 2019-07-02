package com.yc.wsjt.ui.activity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.PayInfo;
import com.yc.wsjt.presenter.Presenter;
import com.yc.wsjt.ui.custom.NoticeDateDialog;
import com.yc.wsjt.ui.custom.PayDateDialog;
import com.yc.wsjt.util.DataUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhangdinghui on 2019/5/29.
 */
public class WxPayMerchantReceiveActivity extends BaseActivity implements NoticeDateDialog.NoticeDateSelectListener, PayDateDialog.PayDateSelectListener {

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

    @BindView(R.id.et_account_number)
    EditText mPayAccountEt;

    @BindView(R.id.et_receive_merchant)
    EditText mMerchantNameEt;

    @BindView(R.id.et_receive_detail)
    EditText mIntoDetailEt;

    NoticeDateDialog noticeDateDialog;

    PayDateDialog payDateDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_wx_pay_merchant_receive;
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
    }

    @Override
    protected void initViews() {
        mTitleTv.setText("商家付款入账通知");
        mNoticeDateEt.setText(TimeUtils.getNowString());
        mPayDateEt.setText(TimeUtils.getNowString());
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

        if (StringUtils.isEmpty(mPayAccountEt.getText())) {
            ToastUtils.showLong("请填写入账账户");
            return;
        }

        if (StringUtils.isEmpty(mMerchantNameEt.getText())) {
            ToastUtils.showLong("请填写商户名称");
            return;
        }

        if (StringUtils.isEmpty(mIntoDetailEt.getText())) {
            ToastUtils.showLong("请填写入账详情");
            return;
        }

        PayInfo payInfo = new PayInfo();
        payInfo.setPayType(PayInfo.RECEIVE_MERCHANT);
        payInfo.setNoticeDate(mNoticeDateEt.getText().toString());
        payInfo.setPayDate(mPayDateEt.getText().toString());
        payInfo.setPayMoney(DataUtils.getMoney(mPayMoneyEt.getText().toString()));
        payInfo.setIntoAccount(mPayAccountEt.getText().toString());
        payInfo.setMerchantName(mMerchantNameEt.getText().toString());
        payInfo.setIntoMoneyRemark(mIntoDetailEt.getText().toString());
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
}
