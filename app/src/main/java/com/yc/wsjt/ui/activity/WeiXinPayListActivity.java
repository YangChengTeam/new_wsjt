package com.yc.wsjt.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.jaeger.library.StatusBarUtil;
import com.yc.wsjt.R;
import com.yc.wsjt.presenter.Presenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhangdinghui on 2019/5/27.
 */
public class WeiXinPayListActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.iv_back)
    ImageView mBackIv;

    @BindView(R.id.tv_title)
    TextView mTitleTv;

    @BindView(R.id.btn_add_data)
    Button mAddDataBtn;

    @BindView(R.id.btn_pre_show)
    Button mQueryDataBtn;

    BottomSheetDialog bottomSheetDialog;

    LinearLayout mVoucherLayout;

    LinearLayout mVoucherMerchantLayout;

    LinearLayout mReceiveCodeLayout;

    LinearLayout mMoneyStartLayout;

    LinearLayout mMoneyEndLayout;

    LinearLayout mReceiveByMerchantLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pay_list;
    }

    @Override
    protected Presenter getPresenter() {
        return null;
    }

    @Override
    protected void initVars() {
        StatusBarUtil.setLightMode(this);
    }

    @Override
    protected void initViews() {
        mTitleTv.setText("微信支付");

        bottomSheetDialog = new BottomSheetDialog(this);
        View payTypeView = LayoutInflater.from(this).inflate(R.layout.add_wx_pay_type_view, null);

        mVoucherLayout = payTypeView.findViewById(R.id.layout_voucher);
        mVoucherMerchantLayout = payTypeView.findViewById(R.id.layout_voucher_merchant);
        mReceiveCodeLayout = payTypeView.findViewById(R.id.layout_receive_in_code);
        mMoneyStartLayout = payTypeView.findViewById(R.id.layout_money_start);
        mMoneyEndLayout = payTypeView.findViewById(R.id.layout_money_end);
        mReceiveByMerchantLayout = payTypeView.findViewById(R.id.layout_receive_by_merchant);

        mVoucherLayout.setOnClickListener(this);
        mVoucherMerchantLayout.setOnClickListener(this);
        mReceiveCodeLayout.setOnClickListener(this);
        mMoneyStartLayout.setOnClickListener(this);
        mMoneyEndLayout.setOnClickListener(this);
        mReceiveByMerchantLayout.setOnClickListener(this);

        bottomSheetDialog.setContentView(payTypeView);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @OnClick(R.id.btn_add_data)
    void add() {
        if (bottomSheetDialog != null && !bottomSheetDialog.isShowing()) {
            bottomSheetDialog.show();
        }
    }

    @OnClick(R.id.btn_pre_show)
    void preShow() {
        Intent intent = new Intent(this, WeiXinPayListPreActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        if (bottomSheetDialog != null && bottomSheetDialog.isShowing()) {
            bottomSheetDialog.dismiss();
        }
        switch (v.getId()) {
            case R.id.layout_voucher:
                Intent intent = new Intent(this, WxPayPersonActivity.class);
                startActivity(intent);
                break;
            case R.id.layout_voucher_merchant:
                Intent intent1 = new Intent(this, WxPayMerchantActivity.class);
                startActivity(intent1);
                break;
            case R.id.layout_receive_in_code:
                Intent intent2 = new Intent(this, WxPayReceiveCodeActivity.class);
                startActivity(intent2);
                break;
            case R.id.layout_money_start:
                Intent intent3 = new Intent(this, WxPayStartActivity.class);
                startActivity(intent3);
                break;
            case R.id.layout_money_end:
                Intent intent4 = new Intent(this, WxPayEndActivity.class);
                startActivity(intent4);
                break;
            case R.id.layout_receive_by_merchant:
                Intent intent5 = new Intent(this, WxPayMerchantReceiveActivity.class);
                startActivity(intent5);
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
