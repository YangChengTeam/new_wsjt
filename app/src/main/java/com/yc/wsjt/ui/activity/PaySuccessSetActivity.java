package com.yc.wsjt.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.kyleduo.switchbutton.SwitchButton;
import com.yc.wsjt.App;
import com.yc.wsjt.R;
import com.yc.wsjt.presenter.Presenter;
import com.yc.wsjt.ui.custom.SettingRoleDialog;
import com.yc.wsjt.util.StringUtils;

import java.text.DecimalFormat;

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

    @BindView(R.id.tv_pay_type)
    TextView mPayTypeTv;

    @BindView(R.id.layout_group_transfer)
    LinearLayout mTransferLayout;

    @BindView(R.id.layout_group_receive)
    LinearLayout mReceiveLayout;

    @BindView(R.id.layout_group_merchant)
    LinearLayout mGroupMerchantLayout;

    @BindView(R.id.et_wx_name)
    EditText mWeixinNameEt;

    @BindView(R.id.et_transfer_money)
    EditText mTransferMoneyEt;

    @BindView(R.id.et_real_name)
    EditText mRealNameEt;

    @BindView(R.id.et_pay_money)
    EditText mPayMoneyEt;

    @BindView(R.id.tv_receive_user_name)
    TextView mReceiveUserNameTv;

    @BindView(R.id.iv_receive_user_head)
    ImageView mReceiveUserHeadIv;

    @BindView(R.id.et_merchant_name)
    EditText mMerchantNameEt;

    @BindView(R.id.et_merchant_money)
    EditText mMerchantMoneyEt;

    @BindView(R.id.sb_follow_merchant)
    SwitchButton mFollowMerchantBtn;

    BottomSheetDialog bottomSheetDialog;

    LinearLayout mPersonTransferLayout;

    LinearLayout mPersonReceiveLayout;

    LinearLayout mMerchantLayout;

    private int chooseType = 1;

    SettingRoleDialog settingRoleDialog;

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

        settingRoleDialog = new SettingRoleDialog(this, R.style.custom_dialog);

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

    @Override
    protected void onResume() {
        super.onResume();
        if (App.getApp().getTempPerson() != null) {
            mReceiveUserNameTv.setText(App.getApp().getTempPerson().mName);
            Glide.with(this).load(App.getApp().getTempPerson().mHead).into(mReceiveUserHeadIv);
        }
    }

    @OnClick(R.id.layout_receive_user)
    void chooseReceiveUser() {
        if (settingRoleDialog != null && !settingRoleDialog.isShowing()) {
            settingRoleDialog.setType(2);
            settingRoleDialog.show();
        }
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
                mPayTypeTv.setText("向个人转账");
                break;
            case R.id.layout_person_receive:
                chooseType = 2;
                mTransferLayout.setVisibility(View.GONE);
                mReceiveLayout.setVisibility(View.VISIBLE);
                mGroupMerchantLayout.setVisibility(View.GONE);
                mPayTypeTv.setText("向个人收款");
                break;
            case R.id.layout_send_merchant:
                chooseType = 3;
                mTransferLayout.setVisibility(View.GONE);
                mReceiveLayout.setVisibility(View.GONE);
                mGroupMerchantLayout.setVisibility(View.VISIBLE);
                mPayTypeTv.setText("向商户付款");
                break;
            default:
                break;
        }
    }

    @OnClick(R.id.btn_show_pre)
    void preShow() {
        switch (chooseType) {
            case 1:
                if (StringUtils.isEmpty(mWeixinNameEt.getText())) {
                    ToastUtils.showLong("请输入收款人微信昵称");
                    return;
                }

                if (StringUtils.isEmpty(mTransferMoneyEt.getText())) {
                    ToastUtils.showLong("请输入支付金额");
                    return;
                }

                DecimalFormat df = new DecimalFormat(".00");
                String temp = df.format(Double.parseDouble(mTransferMoneyEt.getText().toString()));
                Intent intent = new Intent(this, PaySuccessTransferActivity.class);
                intent.putExtra("wx_name", mWeixinNameEt.getText());
                intent.putExtra("wx_money", temp);

                startActivity(intent);
                break;
            case 2:
                if (mReceiveUserNameTv.getText().equals("请选择")) {
                    ToastUtils.showLong("请选择收款人");
                    return;
                }

                if (StringUtils.isEmpty(mPayMoneyEt.getText())) {
                    ToastUtils.showLong("请输入支付金额");
                    return;
                }

                Intent intent1 = new Intent(this, PaySuccessReceiveActivity.class);

                DecimalFormat df1 = new DecimalFormat(".00");
                String temp1 = df1.format(Double.parseDouble(mPayMoneyEt.getText().toString()));

                intent1.putExtra("receive_name", mReceiveUserNameTv.getText());
                intent1.putExtra("receive_user_head", App.getApp().getTempPerson() != null ? App.getApp().getTempPerson().mHead : "");
                intent1.putExtra("wx_money", temp1);

                startActivity(intent1);
                break;
            case 3:

                if (StringUtils.isEmpty(mMerchantNameEt.getText())) {
                    ToastUtils.showLong("请输入商户名称");
                    return;
                }

                if (StringUtils.isEmpty(mMerchantMoneyEt.getText())) {
                    ToastUtils.showLong("请输入支付金额");
                    return;
                }

                Intent intent2 = new Intent(this, PaySuccessMerchantActivity.class);

                DecimalFormat df2 = new DecimalFormat(".00");
                String temp2 = df2.format(Double.parseDouble(mMerchantMoneyEt.getText().toString()));

                intent2.putExtra("wx_merchant_name", mMerchantNameEt.getText().toString());
                intent2.putExtra("follow_merchant", mFollowMerchantBtn.isChecked());
                intent2.putExtra("wx_money", temp2);

                startActivity(intent2);
                break;
            default:
                break;
        }
    }

    @OnClick(R.id.iv_back)
    void back() {
        finish();
    }
}
