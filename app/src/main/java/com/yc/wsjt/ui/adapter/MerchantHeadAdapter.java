package com.yc.wsjt.ui.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.MerchantInfo;

import java.util.List;


public class MerchantHeadAdapter extends BaseQuickAdapter<MerchantInfo, BaseViewHolder> {

    private Context mContext;

    public MerchantHeadAdapter(Context context, List<MerchantInfo> datas) {
        super(R.layout.merchant_head_item, datas);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder holder, MerchantInfo merchantInfo) {
        holder.setText(R.id.tv_merchant_name, merchantInfo.getMerchantName());
        Glide.with(mContext).load(merchantInfo.getMerchantImg()).into((ImageView) holder.getView(R.id.iv_merchant_head));
    }
}