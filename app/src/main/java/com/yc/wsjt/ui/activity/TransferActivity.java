package com.yc.wsjt.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.ScreenUtils;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.jaeger.library.StatusBarUtil;
import com.orhanobut.logger.Logger;
import com.yc.wsjt.R;
import com.yc.wsjt.presenter.Presenter;
import com.yc.wsjt.ui.custom.CustomDateDialog;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 转账-类型
 */
public class TransferActivity extends BaseActivity implements CustomDateDialog.DateSelectListener, View.OnClickListener {

    private static final int REQUEST_CODE_CHOOSE = 1000;

    @BindView(R.id.btn_config)
    Button mConfigBtn;

    @BindView(R.id.layout_send_time)
    RelativeLayout mSendTimeLayout;

    @BindView(R.id.layout_receive_time)
    RelativeLayout mReceiveTimeLayout;

    @BindView(R.id.tv_receive_transfer)
    TextView mReceiveTransferTv;

    @BindView(R.id.tv_turn_out_transfer)
    TextView mTurnOutTv;

    @BindView(R.id.layout_other_side_name)
    LinearLayout mOtherSideNameLayout;

    @BindView(R.id.layout_tranfer_state)
    RelativeLayout mStateLayout;

    @BindView(R.id.tv_state_txt)
    TextView mStateTv;

    @BindView(R.id.tv_send_transfer_time)
    TextView mSendTimeTv;

    @BindView(R.id.tv_receive_transfer_time)
    TextView mReceiveTimeTv;

    @BindView(R.id.layout_show_money)
    LinearLayout mShowMoneyLayout;

    private File outputImage;

    private int chooseType = 1;//转账(1)，收款

    CustomDateDialog customDateDialog;

    BottomSheetDialog bottomSheetDialog;

    LinearLayout mReceiveMoneyLayout;

    LinearLayout mWaitMoneyLayout;

    LinearLayout mRefundLayout;

    LinearLayout mCancelLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_transfer;
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

        customDateDialog = new CustomDateDialog(this, R.style.date_dialog);
        customDateDialog.setDateSelectListener(this);

        bottomSheetDialog = new BottomSheetDialog(this);
        View connectView = LayoutInflater.from(this).inflate(R.layout.money_state_view, null);
        mReceiveMoneyLayout = connectView.findViewById(R.id.layout_receive_money);
        mWaitMoneyLayout = connectView.findViewById(R.id.layout_wait_money);
        mRefundLayout = connectView.findViewById(R.id.layout_refund);
        mCancelLayout = connectView.findViewById(R.id.layout_view_cancel);

        mReceiveMoneyLayout.setOnClickListener(this);
        mWaitMoneyLayout.setOnClickListener(this);
        mRefundLayout.setOnClickListener(this);
        mCancelLayout.setOnClickListener(this);

        bottomSheetDialog.setContentView(connectView);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @OnClick(R.id.layout_send_time)
    void sendTime() {
        chooseType = 1;
        customDateDialog.show();

        //设置Dialog从窗体底部弹出
        customDateDialog.getWindow().setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams windowParams = customDateDialog.getWindow().getAttributes();
        windowParams.width = ScreenUtils.getScreenWidth();
        windowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        customDateDialog.getWindow().setAttributes(windowParams);
    }

    @OnClick(R.id.layout_receive_time)
    void receiveTime() {
        chooseType = 2;
        customDateDialog.show();

        //设置Dialog从窗体底部弹出
        customDateDialog.getWindow().setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams windowParams = customDateDialog.getWindow().getAttributes();
        windowParams.width = ScreenUtils.getScreenWidth();
        windowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        customDateDialog.getWindow().setAttributes(windowParams);
    }


    @OnClick(R.id.tv_receive_transfer)
    void sendTransfer() {
        mReceiveTransferTv.setBackgroundResource(R.drawable.choose_type_selected);
        mReceiveTransferTv.setTextColor(ContextCompat.getColor(this, R.color.white));

        mTurnOutTv.setBackgroundResource(R.drawable.choose_type_normal);
        mTurnOutTv.setTextColor(ContextCompat.getColor(this, R.color.black));
        mOtherSideNameLayout.setVisibility(View.GONE);
        mShowMoneyLayout.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.tv_turn_out_transfer)
    void receiveTransfer() {
        mReceiveTransferTv.setBackgroundResource(R.drawable.choose_type_normal);
        mReceiveTransferTv.setTextColor(ContextCompat.getColor(this, R.color.black));

        mTurnOutTv.setBackgroundResource(R.drawable.choose_type_selected);
        mTurnOutTv.setTextColor(ContextCompat.getColor(this, R.color.white));
        mOtherSideNameLayout.setVisibility(View.VISIBLE);
        mShowMoneyLayout.setVisibility(View.GONE);
    }

    @OnClick(R.id.btn_config)
    void config() {
        finish();
    }

    @Override
    public void configDate(String selectDate) {
        Logger.i("select date --->" + selectDate);
        if (chooseType == 1) {
            mSendTimeTv.setText(selectDate);
        } else {
            mReceiveTimeTv.setText(selectDate);
        }
    }

    @OnClick(R.id.layout_tranfer_state)
    void transferState() {
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
            case R.id.layout_receive_money:
                mStateTv.setText("已收钱");
                break;
            case R.id.layout_wait_money:
                mStateTv.setText("待收款");
                break;
            case R.id.layout_refund:
                mStateTv.setText("已退钱");
                break;
            case R.id.layout_view_cancel:
                break;
            default:
                break;
        }
    }

    @OnClick(R.id.btn_show_pre)
    void preShow() {
        Intent intent = new Intent(this, ReceiveMoneyActivity.class);
        intent.putExtra("receive_type",2);
        startActivity(intent);
    }
}
