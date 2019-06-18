package com.yc.wsjt.ui.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.MoneyDetailInfo;

import java.util.List;

/**
 * 零钱明细
 */
public class MoneyDetailListPreAdapter extends BaseQuickAdapter<MoneyDetailInfo, BaseViewHolder> {

    private Context mContext;

    private int tempWidth;

    public MoneyDetailListPreAdapter(Context context, List<MoneyDetailInfo> datas, int tempWidth) {
        super(R.layout.money_detail_pre_item, datas);
        this.mContext = context;
        this.tempWidth = tempWidth;
    }

    @Override
    protected void convert(BaseViewHolder holder, MoneyDetailInfo moneyDetailInfo) {
        holder.setText(R.id.tv_title, moneyDetailInfo.getTitle())
                .setText(R.id.tv_transaction_time, moneyDetailInfo.getTransactionDate())
                .setText(R.id.tv_money, moneyDetailInfo.getTransactionMoney());
    }
}