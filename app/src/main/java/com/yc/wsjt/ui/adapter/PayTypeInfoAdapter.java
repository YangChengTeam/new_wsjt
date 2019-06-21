package com.yc.wsjt.ui.adapter;

import android.content.Context;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.PayInfo;

import java.util.List;

/**
 * 微信支付显示列表
 */
public class PayTypeInfoAdapter extends BaseQuickAdapter<PayInfo, BaseViewHolder> {

    private Context mContext;

    private String[] payTypeNames = {"微信支付凭证", "微信支付凭证", "收款到账通知", "零钱提现发起", "零钱提现到账", "微信支付凭证"};

    public PayTypeInfoAdapter(Context context, List<PayInfo> datas) {
        super(R.layout.pay_type_info_item, datas);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder holder, PayInfo payInfo) {

        holder.setText(R.id.tv_pay_title, payTypeNames[payInfo.getPayType() - 1])
                .setText(R.id.tv_pay_time, payInfo.getPayDate());

        TextView moneyTv = holder.getView(R.id.tv_money);
        if (payInfo.getPayType() == 3) {
            moneyTv.setText("+" + payInfo.getThisReceiveMoney());
            moneyTv.setTextColor(ContextCompat.getColor(mContext, R.color.profit_color));
        } else {
            moneyTv.setText("-" + payInfo.getPayMoney());
            moneyTv.setTextColor(ContextCompat.getColor(mContext, R.color.gray0));
        }
    }
}