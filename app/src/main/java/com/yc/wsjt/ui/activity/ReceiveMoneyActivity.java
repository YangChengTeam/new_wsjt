package com.yc.wsjt.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.jaeger.library.StatusBarUtil;
import com.orhanobut.logger.Logger;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.MessageEvent;
import com.yc.wsjt.common.Constants;
import com.yc.wsjt.presenter.Presenter;
import com.yc.wsjt.ui.custom.VipPayTypeDialog;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

public class ReceiveMoneyActivity extends BaseActivity implements VipPayTypeDialog.PayListener {

    private int chooseType = 1;

    @BindView(R.id.iv_back)
    ImageView mBackIv;

    @BindView(R.id.iv_receive_img)
    ImageView mReceiveStateIv;

    @BindView(R.id.tv_receive_txt)
    TextView mReceiveStateTv;

    @BindView(R.id.tv_money)
    TextView mMoneyTv;

    @BindView(R.id.tv_look_money)
    TextView mLookMoneyTv;

    @BindView(R.id.layout_wait)
    LinearLayout mWaitLayout;

    @BindView(R.id.layout_profit)
    LinearLayout mProfitLayout;

    @BindView(R.id.tv_profit_remark)
    TextView mProfitRemarkTv;

    @BindView(R.id.tv_send_time)
    TextView mSendTimeTv;

    @BindView(R.id.tv_receive_time)
    TextView mReceiveTimeTv;

    private int transState = 1;

    private boolean isShowMoney;

    private String transMoney;

    private boolean isUse;

    private boolean isFromChat;

    private int mid;

    private int chatPos;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_receive_money;
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
        vipPayTypeDialog.setPayListener(this);

        mLookMoneyTv.setText("查看零钱");
        mLookMoneyTv.setTextColor(ContextCompat.getColor(this, R.color.look_money_color));

        Bundle bundle = getIntent().getExtras();
        isUse = bundle.getBoolean("is_use", false);
        isFromChat = bundle.getBoolean("is_from_chat", false);
        mid = bundle.getInt("mid", 0);
        chatPos = bundle.getInt("chat_position",chatPos);
        if (bundle.getInt("choose_type") > 0) {
            chooseType = bundle.getInt("choose_type");
        }
        if (chooseType == 1) {
            transState = bundle.getInt("trans_state");
            if (transState == 1) {
                Glide.with(this).load(R.mipmap.trans_success).into(mReceiveStateIv);
                mReceiveStateTv.setText("已收钱");
                mWaitLayout.setVisibility(View.GONE);
            }
            if (transState == 2) {
                Glide.with(this).load(R.mipmap.trans_wait).into(mReceiveStateIv);
                mReceiveStateTv.setText("待确认收款");
                mWaitLayout.setVisibility(View.VISIBLE);
                mProfitLayout.setVisibility(View.GONE);
                mLookMoneyTv.setVisibility(View.GONE);
            }
            if (transState == 3) {
                Glide.with(this).load(R.mipmap.trans_back).into(mReceiveStateIv);
                mReceiveStateTv.setText("已退还");
                mWaitLayout.setVisibility(View.GONE);
            }
            mMoneyTv.setText(bundle.getString("trans_money"));

            if (transState != 2) {
                mProfitLayout.setVisibility(bundle.getBoolean("show_profit", false) ? View.VISIBLE : View.GONE);
            }

            mSendTimeTv.setText("转账时间：" + bundle.getString("send_time"));
            mReceiveTimeTv.setText("收款时间：" + bundle.getString("receive_time"));

        } else {
            mLookMoneyTv.setText("已存入对方零钱中");
            mLookMoneyTv.setTextColor(ContextCompat.getColor(this, R.color.transfer_color));
            mProfitLayout.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        if (!isUse) {
            if (openVipDialog != null && !openVipDialog.isShowing()) {
                openVipDialog.show();
                return;
            }
        }
    }

    @OnClick(R.id.btn_config_receive)
    void configReceive() {
        MessageEvent event = new MessageEvent();
        event.setMid(mid);
        event.setChatPos(chatPos);
        event.setMessageType(Constants.CONFIG_RECEIVE_MONEY);
        EventBus.getDefault().post(event);
        finish();
    }

    @Override
    public void addComment() {
        super.addComment();
    }

    @Override
    public void closeOpenVip() {
        super.closeOpenVip();
        finish();
    }

    @Override
    public void pay() {
        Logger.i("打开支付--->");
    }

    @Override
    public void payClose() {
        Logger.i("支付界面关闭--->");
        finish();
    }

    @OnClick(R.id.iv_back)
    void back() {
        finish();
    }
}
