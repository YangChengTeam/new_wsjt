package com.yc.wsjt.ui.activity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;
import com.yc.wsjt.R;
import com.yc.wsjt.presenter.Presenter;
import com.yc.wsjt.ui.custom.NoticeDateDialog;
import com.yc.wsjt.ui.custom.PayDateDialog;

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
