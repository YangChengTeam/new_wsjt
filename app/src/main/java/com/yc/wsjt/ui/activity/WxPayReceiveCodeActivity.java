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
 * 二维码收款到账
 */
public class WxPayReceiveCodeActivity extends BaseActivity implements NoticeDateDialog.NoticeDateSelectListener, PayDateDialog.PayDateSelectListener {

    @BindView(R.id.iv_back)
    ImageView mBackIv;

    @BindView(R.id.tv_title)
    TextView mTitleTv;

    @BindView(R.id.et_notice_time)
    EditText mNoticeDateEt;

    @BindView(R.id.et_pay_time)
    EditText mPayDateEt;

    @BindView(R.id.et_receive_money)
    EditText mReceiveMoneyEt;

    @BindView(R.id.et_total_money)
    EditText mTotalMoneyEt;

    @BindView(R.id.et_total_number)
    EditText mTotalCountEt;

    @BindView(R.id.et_pay_remark)
    EditText mPayRemarkEt;

    NoticeDateDialog noticeDateDialog;

    PayDateDialog payDateDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_wx_pay_receive_code;
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
        mTitleTv.setText("二维码收款到账通知");
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
        if (StringUtils.isEmpty(mPayDateEt.getText())) {
            ToastUtils.showLong("请输入支付时间");
            return;
        }

        if (StringUtils.isEmpty(mReceiveMoneyEt.getText())) {
            ToastUtils.showLong("请填写本次收款金额");
            return;
        }

        if (StringUtils.isEmpty(mTotalMoneyEt.getText())) {
            ToastUtils.showLong("请填写累计收款金额");
            return;
        }

        if (StringUtils.isEmpty(mTotalCountEt.getText())) {
            ToastUtils.showLong("请填写累计收款笔数");
            return;
        }

        PayInfo payInfo = new PayInfo();
        payInfo.setPayType(PayInfo.RECEIVE_CODE);
        payInfo.setNoticeDate(mNoticeDateEt.getText().toString());
        payInfo.setPayDate(mPayDateEt.getText().toString());
        payInfo.setThisReceiveMoney(DataUtils.getMoney(mReceiveMoneyEt.getText().toString()));
        payInfo.setTotalMoney(DataUtils.getMoney(mTotalMoneyEt.getText().toString()));
        payInfo.setTotalCount(Integer.parseInt(mTotalCountEt.getText().toString()));
        payInfo.setPayRemark(mPayRemarkEt.getText().toString());
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
