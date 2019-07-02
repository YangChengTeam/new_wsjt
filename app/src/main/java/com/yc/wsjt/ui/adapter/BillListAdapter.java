package com.yc.wsjt.ui.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.BillInfo;

import java.util.List;


public class BillListAdapter extends BaseQuickAdapter<BillInfo, BaseViewHolder> {

    private Context mContext;

    public BillListAdapter(Context context, List<BillInfo> datas) {
        super(R.layout.bill_item_view, datas);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder holder, BillInfo billInfo) {
        holder.setText(R.id.tv_bill_title, billInfo.getBillTitle())
                .setText(R.id.tv_bill_date, billInfo.getBillDate());
        TextView moneyTv = holder.getView(R.id.tv_bill_money);
        if (billInfo.getBillType() == 1) {
            moneyTv.setText("+" + billInfo.getBillMoney());
            moneyTv.setTextColor(ContextCompat.getColor(mContext, R.color.profit_color));
        } else {
            moneyTv.setText("-" + billInfo.getBillMoney());
            moneyTv.setTextColor(ContextCompat.getColor(mContext, R.color.gray0));
        }
        holder.setVisible(R.id.tv_refund, billInfo.isRefund());

        if (billInfo.getBillImage() > 0) {
            Glide.with(mContext).load(billInfo.getBillImage()).into((ImageView) holder.getView(R.id.iv_bill_icon));
        } else {
            Glide.with(mContext).load(billInfo.getBillImageUrl()).into((ImageView) holder.getView(R.id.iv_bill_icon));
        }
    }
}