package com.yc.wsjt.ui.custom;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.ToastUtils;
import com.kk.pay.I1PayAbs;
import com.kk.pay.IPayAbs;
import com.kk.pay.IPayCallback;
import com.kk.pay.OrderInfo;
import com.kk.pay.OrderParamsInfo;
import com.yc.wsjt.App;
import com.yc.wsjt.R;


public class VipPayTypeDialog extends Dialog implements View.OnClickListener {

    private Context mContext;

    ImageView mCloseIv;

    ImageView mAlipayIv;

    ImageView mWxIv;

    Button mPayBtn;

    TextView mThreeMonth;

    TextView mHalfYear;

    TextView mOneYear;

    private IPayAbs iPayAbs;

    String payWayName = "alipay";

    public interface PayListener {
        void pay();
    }

    public PayListener payListener;

    public void setPayListener(PayListener payListener) {
        this.payListener = payListener;
    }

    public VipPayTypeDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public VipPayTypeDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
        iPayAbs = new I1PayAbs((Activity) context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vip_type_select_view);
        setCanceledOnTouchOutside(true);

        initView();
    }

    private void initView() {
        mCloseIv = findViewById(R.id.iv_close);
        mAlipayIv = findViewById(R.id.iv_alipay_choose);
        mWxIv = findViewById(R.id.iv_wx_choose);
        mPayBtn = findViewById(R.id.btn_pay);

        mThreeMonth = findViewById(R.id.tv_three_month);
        mHalfYear = findViewById(R.id.tv_half_year);
        mOneYear = findViewById(R.id.tv_one_year);

        mCloseIv.setOnClickListener(this);
        mAlipayIv.setOnClickListener(this);
        mWxIv.setOnClickListener(this);
        mPayBtn.setOnClickListener(this);
        mThreeMonth.setOnClickListener(this);
        mHalfYear.setOnClickListener(this);
        mOneYear.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_close:
                dismiss();
                break;
            case R.id.iv_alipay_choose:
                mAlipayIv.setImageResource(R.mipmap.pay_selected_icon);
                mWxIv.setImageResource(R.mipmap.pay_normal_icon);
                payWayName = "alipay";
                break;
            case R.id.iv_wx_choose:
                mAlipayIv.setImageResource(R.mipmap.pay_normal_icon);
                mWxIv.setImageResource(R.mipmap.pay_selected_icon);
                payWayName = "wxpay";
                break;
            case R.id.btn_pay:
                dismiss();
                //payListener.pay();

                String userId = App.getApp().mUserInfo != null ? App.getApp().mUserInfo.getId() : "";
                OrderParamsInfo orderParamsInfo = new OrderParamsInfo("http://wsjt.zhuoyi51.com/api/pay/index", "1", "1", 1f, "VIP会员", "10000");
                orderParamsInfo.setPayway_name(payWayName);

                iPayAbs.pay(orderParamsInfo, new IPayCallback() {
                    @Override
                    public void onSuccess(OrderInfo orderInfo) {
                        ToastUtils.showLong("购买成功--->" + JSON.toJSONString(orderInfo));
                        dismiss();
                    }

                    @Override
                    public void onFailure(OrderInfo orderInfo) {
                        //Toast.makeText(context, "购买失败", Toast.LENGTH_LONG).show();
                        dismiss();
                    }
                });

                break;
            case R.id.tv_three_month:
                mThreeMonth.setBackgroundResource(R.drawable.feed_selected);
                mHalfYear.setBackgroundResource(R.drawable.feed_normal);
                mOneYear.setBackgroundResource(R.drawable.feed_normal);

                mThreeMonth.setTextColor(ContextCompat.getColor(mContext, R.color.feed_line_select_color));
                mHalfYear.setTextColor(ContextCompat.getColor(mContext, R.color.tab_tv_normal));
                mHalfYear.setTextColor(ContextCompat.getColor(mContext, R.color.tab_tv_normal));
                break;
            case R.id.tv_half_year:
                mThreeMonth.setBackgroundResource(R.drawable.feed_normal);
                mHalfYear.setBackgroundResource(R.drawable.feed_selected);
                mOneYear.setBackgroundResource(R.drawable.feed_normal);

                mThreeMonth.setTextColor(ContextCompat.getColor(mContext, R.color.tab_tv_normal));
                mHalfYear.setTextColor(ContextCompat.getColor(mContext, R.color.feed_line_select_color));
                mOneYear.setTextColor(ContextCompat.getColor(mContext, R.color.tab_tv_normal));
                break;
            case R.id.tv_one_year:
                mThreeMonth.setBackgroundResource(R.drawable.feed_normal);
                mHalfYear.setBackgroundResource(R.drawable.feed_normal);
                mOneYear.setBackgroundResource(R.drawable.feed_selected);

                mThreeMonth.setTextColor(ContextCompat.getColor(mContext, R.color.tab_tv_normal));
                mHalfYear.setTextColor(ContextCompat.getColor(mContext, R.color.tab_tv_normal));
                mOneYear.setTextColor(ContextCompat.getColor(mContext, R.color.feed_line_select_color));
                break;
        }
    }
}