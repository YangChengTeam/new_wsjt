package com.yc.wsjt.ui.adapter;

import android.content.Context;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.MoneyDetail;

import java.util.List;

/**
 * 零钱明细
 */
public class MoneyDetailListPreAdapter extends BaseQuickAdapter<MoneyDetail, BaseViewHolder> {

    private Context mContext;

    private int tempWidth;

    public MoneyDetailListPreAdapter(Context context, List<MoneyDetail> datas, int tempWidth) {
        super(R.layout.money_detail_pre_item, datas);
        this.mContext = context;
        this.tempWidth = tempWidth;
    }

    @Override
    protected void convert(BaseViewHolder holder, MoneyDetail moneyDetail) {
        holder.setText(R.id.tv_title, moneyDetail.getMoneyTitle())
                .setText(R.id.tv_transaction_time, moneyDetail.getAddTime());
        TextView moneyTv = holder.getView(R.id.tv_money);
        if (moneyDetail.getMoneyType() == 1) {
            moneyTv.setText("+" + moneyDetail.getMoney());
            moneyTv.setTextColor(ContextCompat.getColor(mContext, R.color.profit_color));
        } else {
            moneyTv.setText("-" + moneyDetail.getMoney());
            moneyTv.setTextColor(ContextCompat.getColor(mContext, R.color.gray0));
        }
    }
}