package com.yc.wsjt.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.yc.wsjt.R;
import com.yc.wsjt.presenter.Presenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhangdinghui on 2019/5/29.
 */
public class PaySuccessSetActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.tv_title)
    TextView mTitleTv;

    @BindView(R.id.btn_config)
    Button mConfigBtn;

    @BindView(R.id.layout_group_transfer)
    LinearLayout mTransferLayout;

    @BindView(R.id.layout_group_receive)
    LinearLayout mReceiveLayout;

    @BindView(R.id.layout_group_merchant)
    LinearLayout mGroupMerchantLayout;

    BottomSheetDialog bottomSheetDialog;

    LinearLayout mPersonTransferLayout;

    LinearLayout mPersonReceiveLayout;

    LinearLayout mMerchantLayout;

    private int chooseType = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pay_success_set;
    }

    @Override
    protected Presenter getPresenter() {
        return null;
    }

    @Override
    protected void initVars() {
        mTitleTv.setText("支付成功");

        bottomSheetDialog = new BottomSheetDialog(this);
        View payTypeView = LayoutInflater.from(this).inflate(R.layout.pay_type_view, null);
        mPersonTransferLayout = payTypeView.findViewById(R.id.layout_person_transfer);
        mPersonReceiveLayout = payTypeView.findViewById(R.id.layout_person_receive);
        mMerchantLayout = payTypeView.findViewById(R.id.layout_send_merchant);

        mPersonTransferLayout.setOnClickListener(this);
        mPersonReceiveLayout.setOnClickListener(this);
        mMerchantLayout.setOnClickListener(this);

        bottomSheetDialog.setContentView(payTypeView);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @OnClick(R.id.layout_pay_type)
    void payType() {
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
            case R.id.layout_person_transfer:
                chooseType = 1;
                mTransferLayout.setVisibility(View.VISIBLE);
                mReceiveLayout.setVisibility(View.GONE);
                mGroupMerchantLayout.setVisibility(View.GONE);
                break;
            case R.id.layout_person_receive:
                chooseType = 2;
                mTransferLayout.setVisibility(View.GONE);
                mReceiveLayout.setVisibility(View.VISIBLE);
                mGroupMerchantLayout.setVisibility(View.GONE);
                break;
            case R.id.layout_send_merchant:
                chooseType = 3;
                mTransferLayout.setVisibility(View.GONE);
                mReceiveLayout.setVisibility(View.GONE);
                mGroupMerchantLayout.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

    @OnClick(R.id.btn_show_pre)
    void preShow() {
        switch (chooseType) {
            case 1:
                Intent intent = new Intent(this, PaySuccessTransferActivity.class);
                startActivity(intent);
                break;
            case 2:
                Intent intent1 = new Intent(this, PaySuccessReceiveActivity.class);
                startActivity(intent1);
                break;
            case 3:
                Intent intent2 = new Intent(this, PaySuccessMerchantActivity.class);
                startActivity(intent2);
                break;
            default:
                Intent intent3 = new Intent(this, PaySuccessTransferActivity.class);
                startActivity(intent3);
                break;
        }
    }

    @OnClick(R.id.iv_back)
    void back() {
        finish();
    }
}
