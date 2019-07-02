package com.yc.wsjt.ui.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.orhanobut.logger.Logger;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.PayInfo;
import com.yc.wsjt.presenter.Presenter;
import com.yc.wsjt.ui.custom.Glide4Engine;
import com.yc.wsjt.ui.custom.MerchantHotHeadDialog;
import com.yc.wsjt.ui.custom.NoticeDateDialog;
import com.yc.wsjt.ui.custom.PayDateDialog;
import com.yc.wsjt.util.DataUtils;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhangdinghui on 2019/5/29.
 */
public class WxPayMerchantActivity extends BaseActivity implements NoticeDateDialog.NoticeDateSelectListener, PayDateDialog.PayDateSelectListener, View.OnClickListener, MerchantHotHeadDialog.ChooseHeadListener {

    private static final int REQUEST_CODE_CHOOSE = 1000;

    @BindView(R.id.iv_back)
    ImageView mBackIv;

    @BindView(R.id.tv_title)
    TextView mTitleTv;

    @BindView(R.id.iv_merchant_head)
    ImageView mMerchantHeadIv;

    @BindView(R.id.et_merchant_name)
    EditText mMerchantNameEt;

    @BindView(R.id.et_notice_time)
    EditText mNoticeDateEt;

    @BindView(R.id.et_pay_time)
    EditText mPayDateEt;

    @BindView(R.id.et_pay_money)
    EditText mPayMoneyEt;

    NoticeDateDialog noticeDateDialog;

    PayDateDialog payDateDialog;

    BottomSheetDialog bottomSheetDialog;

    LinearLayout mChooseLayout;

    LinearLayout mCustomLayout;

    LinearLayout mCancelLayout;

    MerchantHotHeadDialog merchantHotHeadDialog;

    WindowManager.LayoutParams windowParams;

    private int merchantHeadUrl;

    private String merchantCustomUrl;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_wx_pay_merchant;
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

        merchantHotHeadDialog = new MerchantHotHeadDialog(this, R.style.custom_dialog);
        merchantHotHeadDialog.setChooseHeadListener(this);
    }

    @Override
    protected void initViews() {
        mTitleTv.setText("支付凭证 (商户)");
        mNoticeDateEt.setText(TimeUtils.getNowString());
        mPayDateEt.setText(TimeUtils.getNowString());

        bottomSheetDialog = new BottomSheetDialog(this);
        View chooseView = LayoutInflater.from(this).inflate(R.layout.merchant_head_choose_view, null);
        mChooseLayout = chooseView.findViewById(R.id.layout_choose);
        mCustomLayout = chooseView.findViewById(R.id.layout_custom);
        mCancelLayout = chooseView.findViewById(R.id.layout_cancel);
        mChooseLayout.setOnClickListener(this);
        mCustomLayout.setOnClickListener(this);
        mCancelLayout.setOnClickListener(this);
        bottomSheetDialog.setContentView(chooseView);
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

    @OnClick(R.id.layout_merchant_head)
    void merchantHead() {
        if (bottomSheetDialog != null && !bottomSheetDialog.isShowing()) {
            bottomSheetDialog.show();
        }
    }

    @Override
    public void onClick(View v) {
        if (bottomSheetDialog != null && bottomSheetDialog.isShowing()) {
            bottomSheetDialog.dismiss();
        }
        switch (v.getId()) {
            case R.id.layout_choose:
                if (merchantHotHeadDialog != null && !merchantHotHeadDialog.isShowing()) {
                    merchantHotHeadDialog.show();
                    merchantHotHeadDialog.setCanceledOnTouchOutside(true);

                    windowParams = merchantHotHeadDialog.getWindow().getAttributes();
                    windowParams.width = (int) (ScreenUtils.getScreenWidth() * 0.92);
                    windowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
                    merchantHotHeadDialog.getWindow().setAttributes(windowParams);
                }
                break;
            case R.id.layout_custom:
                Matisse.from(this)
                        .choose(MimeType.ofImage())
                        .countable(true)
                        .maxSelectable(1)
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                        .thumbnailScale(0.85f)
                        .imageEngine(new Glide4Engine())
                        .showSingleMediaType(true)
                        .forResult(REQUEST_CODE_CHOOSE);
                break;
            case R.id.layout_cancel:

                break;

            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_CHOOSE:
                    Logger.i(JSONObject.toJSONString(Matisse.obtainPathResult(data)));
                    if (Matisse.obtainPathResult(data) != null && Matisse.obtainPathResult(data).size() > 0) {
                        merchantCustomUrl = Matisse.obtainPathResult(data).get(0);
                        Glide.with(this).load(merchantCustomUrl).into(mMerchantHeadIv);
                    }
                    break;
            }
        }
    }

    @OnClick(R.id.btn_config)
    void config() {

        if (merchantHeadUrl == 0 && StringUtils.isEmpty(merchantCustomUrl)) {
            ToastUtils.showLong("请选择商户头像");
            return;
        }

        if (StringUtils.isEmpty(mMerchantNameEt.getText())) {
            ToastUtils.showLong("请填写商户名称");
            return;
        }

        if (StringUtils.isEmpty(mNoticeDateEt.getText())) {
            ToastUtils.showLong("请输入通知时间");
            return;
        }
        if (StringUtils.isEmpty(mPayDateEt.getText())) {
            ToastUtils.showLong("请输入支付时间");
            return;
        }

        if (StringUtils.isEmpty(mPayMoneyEt.getText())) {
            ToastUtils.showLong("请填写金额");
            return;
        }

        PayInfo payInfo = new PayInfo();
        payInfo.setPayType(PayInfo.MERCHANT);
        payInfo.setNoticeDate(mNoticeDateEt.getText().toString());
        payInfo.setPayDate(mPayDateEt.getText().toString());
        payInfo.setPayMoney(DataUtils.getMoney(mPayMoneyEt.getText().toString()));
        payInfo.setMerchantName(StringUtils.isEmpty(mMerchantNameEt.getText()) ? "" : mMerchantNameEt.getText().toString());
        if (merchantHeadUrl > 0) {
            payInfo.setMerchantLocalHead(merchantHeadUrl);
        } else {
            payInfo.setMerchantHead(merchantCustomUrl);
        }
        payInfo.setOpenMiniApp(false);
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
    public void chooseHead(int headUrl, String merName) {
        merchantHeadUrl = headUrl;
        Glide.with(this).load(headUrl).into(mMerchantHeadIv);
        mMerchantNameEt.setText(merName);
    }
}
