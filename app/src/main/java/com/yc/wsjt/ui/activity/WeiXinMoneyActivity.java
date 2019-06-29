package com.yc.wsjt.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jaeger.library.StatusBarUtil;
import com.kyleduo.switchbutton.SwitchButton;
import com.yc.wsjt.R;
import com.yc.wsjt.presenter.Presenter;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.OnClick;

public class WeiXinMoneyActivity extends BaseActivity {

    private int receiveType = 1;

    @BindView(R.id.tv_title)
    TextView mTitleTv;

    @BindView(R.id.btn_config)
    Button mConfigBtn;

    @BindView(R.id.iv_back)
    ImageView mBackIv;

    @BindView(R.id.et_money)
    EditText mMoneyEt;

    @BindView(R.id.btn_show_pre)
    Button mShowPre;

    @BindView(R.id.sb_money_show)
    SwitchButton mMoneyShowBtn;

    @BindView(R.id.layout_show_money)
    LinearLayout mMoneyShowLayout;

    @BindView(R.id.et_profit_remark)
    EditText mProfitRemarkEt;

    private boolean isUse;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_wx_money;
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
        mTitleTv.setText("微信零钱");
        mConfigBtn.setVisibility(View.GONE);
        mMoneyShowBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mMoneyShowLayout.setVisibility(View.VISIBLE);
                } else {
                    mMoneyShowLayout.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            isUse = bundle.getBoolean("is_use", true);
        }
    }

    @OnClick(R.id.btn_show_pre)
    void preShow() {
        if (StringUtils.isEmpty(mMoneyEt.getText())) {
            ToastUtils.showLong("请输入零钱金额");
            return;
        }

        if (!isUse) {
            if (openVipDialog != null && !openVipDialog.isShowing()) {
                openVipDialog.show();
                return;
            }
        }

        Intent intent = new Intent(this, MoneyPreActivity.class);
        DecimalFormat df = new DecimalFormat(".00");
        String temp = df.format(Double.parseDouble(mMoneyEt.getText().toString()));
        intent.putExtra("money", temp);
        intent.putExtra("show_profit", mMoneyShowBtn.isChecked());
        intent.putExtra("profit_remark", mProfitRemarkEt.getText());
        startActivity(intent);
    }

    @OnClick(R.id.iv_back)
    void back() {
        finish();
    }
}
