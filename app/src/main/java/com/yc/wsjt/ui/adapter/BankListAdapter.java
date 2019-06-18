package com.yc.wsjt.ui.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.BankInfo;

import java.util.List;


public class BankListAdapter extends BaseQuickAdapter<BankInfo, BaseViewHolder> {

    private Context mContext;

    public BankListAdapter(Context context, List<BankInfo> datas) {
        super(R.layout.bank_item_view, datas);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder holder, BankInfo bankInfo) {
        holder.setText(R.id.tv_bank_name, bankInfo.getBankName());
        Glide.with(mContext).load(bankInfo.getBankImg()).into((ImageView) holder.getView(R.id.iv_bank_img));
    }
}