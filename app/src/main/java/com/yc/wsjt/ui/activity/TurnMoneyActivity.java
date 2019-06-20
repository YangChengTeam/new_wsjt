package com.yc.wsjt.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jaeger.library.StatusBarUtil;
import com.yc.wsjt.R;
import com.yc.wsjt.presenter.Presenter;

import butterknife.BindView;
import butterknife.OnClick;

public class TurnMoneyActivity extends BaseActivity {

    private int chooseType = 1;

    @BindView(R.id.iv_back)
    ImageView mBackIv;

    @BindView(R.id.iv_receive_img)
    ImageView mReceiveStateIv;

    @BindView(R.id.tv_receive_txt)
    TextView mReceiveStateTv;

    @BindView(R.id.tv_money)
    TextView mMoneyTv;

    @BindView(R.id.tv_turn_info)
    TextView mTurnInfoTv;

    @BindView(R.id.tv_turn_remark)
    TextView mTurnRemarkTv;

    @BindView(R.id.tv_send_time)
    TextView mSendTimeTv;

    @BindView(R.id.tv_receive_time)
    TextView mReceiveTimeTv;

    private int transState = 1;

    private boolean isShowMoney;

    private String transMoney;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_turn_money;
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
        Bundle bundle = getIntent().getExtras();
        transState = bundle.getInt("trans_state");

        String otherSideName = bundle.getString("nick_name");

        if (transState == 1) {
            Glide.with(this).load(R.mipmap.trans_success).into(mReceiveStateIv);
            mReceiveStateTv.setText(otherSideName + "已收款");
            mTurnInfoTv.setText("已存入对方零钱中");
            mTurnRemarkTv.setVisibility(View.GONE);
        }
        if (transState == 2) {
            Glide.with(this).load(R.mipmap.trans_wait).into(mReceiveStateIv);
            mReceiveStateTv.setText("待" + otherSideName + "确认收款");
            mTurnInfoTv.setText("1天内朋友未确认，将退还给你。");
            mTurnRemarkTv.setVisibility(View.VISIBLE);
            mTurnRemarkTv.setText("提醒对方收款");
        }
        if (transState == 3) {
            Glide.with(this).load(R.mipmap.trans_back).into(mReceiveStateIv);
            mReceiveStateTv.setText(otherSideName + "已退还");
            mTurnInfoTv.setText("已退回到零钱");
            mTurnRemarkTv.setText("查看零钱");
            mTurnRemarkTv.setVisibility(View.VISIBLE);
        }

        mMoneyTv.setText(bundle.getString("trans_money"));
        mSendTimeTv.setText("转账时间：" + bundle.getString("send_time"));
        mReceiveTimeTv.setText("收款时间：" + bundle.getString("receive_time"));
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @OnClick(R.id.iv_back)
    void back() {
        finish();
    }
}
