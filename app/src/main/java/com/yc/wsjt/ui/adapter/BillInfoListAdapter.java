package com.yc.wsjt.ui.adapter;

import android.content.Context;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.BillInfo;

import java.util.List;

/**
 * 微信支付显示列表
 */
public class BillInfoListAdapter extends BaseQuickAdapter<BillInfo, BaseViewHolder> {

    private Context mContext;

    public BillInfoListAdapter(Context context, List<BillInfo> datas) {
        super(R.layout.bill_info_item, datas);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder holder, BillInfo billInfo) {

        holder.setText(R.id.tv_bill_title, billInfo.getBillTitle())
                .setText(R.id.tv_bill_time, billInfo.getBillDate());

        TextView moneyTv = holder.getView(R.id.tv_money);
        if (billInfo.getBillType() == 1) {
            moneyTv.setText("+" + billInfo.getBillMoney());
            moneyTv.setTextColor(ContextCompat.getColor(mContext, R.color.profit_color));
        } else {
            moneyTv.setText("-" + billInfo.getBillMoney());
            moneyTv.setTextColor(ContextCompat.getColor(mContext, R.color.gray0));
        }
    }
}