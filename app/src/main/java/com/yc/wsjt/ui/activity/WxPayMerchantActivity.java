package com.yc.wsjt.ui.activity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.yc.wsjt.R;
import com.yc.wsjt.presenter.Presenter;
import com.yc.wsjt.ui.custom.MerchantHotHeadDialog;
import com.yc.wsjt.ui.custom.NoticeDateDialog;
import com.yc.wsjt.ui.custom.PayDateDialog;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhangdinghui on 2019/5/29.
 */
public class WxPayMerchantActivity extends BaseActivity implements NoticeDateDialog.NoticeDateSelectListener, PayDateDialog.PayDateSelectListener, View.OnClickListener {

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

    BottomSheetDialog bottomSheetDialog;

    LinearLayout mChooseLayout;

    LinearLayout mCustomLayout;

    LinearLayout mCancelLayout;

    MerchantHotHeadDialog merchantHotHeadDialog;

    WindowManager.LayoutParams windowParams;

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
    }

    @Override
    protected void initViews() {
        mTitleTv.setText("支付凭证 (商户)");

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

                break;
            case R.id.layout_cancel:

                break;

            default:
                break;
        }
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
