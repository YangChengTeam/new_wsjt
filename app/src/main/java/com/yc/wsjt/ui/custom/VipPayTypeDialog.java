package com.yc.wsjt.ui.custom;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kk.pay.I1PayAbs;
import com.kk.pay.IPayAbs;
import com.kk.pay.IPayCallback;
import com.kk.pay.OrderInfo;
import com.kk.pay.OrderParamsInfo;
import com.orhanobut.logger.Logger;
import com.yc.wsjt.App;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.VipInfo;
import com.yc.wsjt.bean.VipInfoRet;
import com.yc.wsjt.common.Constants;
import com.yc.wsjt.presenter.VipInfoPresenterImp;
import com.yc.wsjt.ui.adapter.VipListAdapter;
import com.yc.wsjt.view.VipInfoView;


public class VipPayTypeDialog extends Dialog implements View.OnClickListener, VipInfoView {

    private Context mContext;

    ImageView mCloseIv;

    ImageView mAlipayIv;

    ImageView mWxIv;

    Button mPayBtn;

    RecyclerView mVipListView;

    private IPayAbs iPayAbs;

    String payWayName = "alipay";

    private VipInfoPresenterImp vipInfoPresenterImp;

    VipListAdapter vipListAdapter;

    int lastSelectPos = 0;

    private VipInfo payVipInfo;

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

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vip_type_select_view);
        setCanceledOnTouchOutside(true);

        iPayAbs = new I1PayAbs((Activity) mContext);

        initView();
    }

    private void initView() {
        mCloseIv = findViewById(R.id.iv_close);
        mAlipayIv = findViewById(R.id.iv_alipay_choose);
        mWxIv = findViewById(R.id.iv_wx_choose);
        mPayBtn = findViewById(R.id.btn_pay);
        mVipListView = findViewById(R.id.vip_list);
        mCloseIv.setOnClickListener(this);
        mAlipayIv.setOnClickListener(this);
        mWxIv.setOnClickListener(this);
        mPayBtn.setOnClickListener(this);

        vipListAdapter = new VipListAdapter(mContext, null);
        mVipListView.setLayoutManager(new GridLayoutManager(mContext, 3));
        mVipListView.setAdapter(vipListAdapter);

        vipListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (lastSelectPos == position) {
                    return;
                }
                payVipInfo = vipListAdapter.getData().get(position);
                vipListAdapter.getData().get(position).setChecked(true);
                vipListAdapter.getData().get(lastSelectPos).setChecked(false);
                lastSelectPos = position;

                vipListAdapter.notifyDataSetChanged();
            }
        });

        vipInfoPresenterImp = new VipInfoPresenterImp(this, mContext);
        vipInfoPresenterImp.getVipList();
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void dismissProgress() {

    }

    @Override
    public void loadDataSuccess(VipInfoRet tData) {
        if (tData != null && tData.getCode() == Constants.SUCCESS) {
            Logger.i(JSON.toJSONString(tData));
            if (tData.getData() != null && tData.getData().size() > 0) {
                vipListAdapter.setNewData(tData.getData());
                vipListAdapter.getData().get(0).setChecked(true);
                payVipInfo = vipListAdapter.getData().get(0);
                vipListAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void loadDataError(Throwable throwable) {

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
                if (App.getApp().mUserInfo == null) {
                    ToastUtils.showLong("请先登录后开通VIP");
                    return;
                }
                dismiss();

                String userId = App.getApp().mUserInfo != null ? App.getApp().mUserInfo.getId() : "";
                OrderParamsInfo orderParamsInfo = new OrderParamsInfo(Constants.PAY_URL, payVipInfo.getId(), "1", payVipInfo.getPrice(), payVipInfo.getName(), userId);
                orderParamsInfo.setPayway_name(payWayName);

                iPayAbs.pay(orderParamsInfo, new IPayCallback() {
                    @Override
                    public void onSuccess(OrderInfo orderInfo) {
                        ToastUtils.showLong("购买成功" + JSON.toJSONString(orderInfo));
                        dismiss();
                    }

                    @Override
                    public void onFailure(OrderInfo orderInfo) {
                        ToastUtils.showLong("购买失败");
                        dismiss();
                    }
                });
                break;
            default:
                break;
        }
    }
}