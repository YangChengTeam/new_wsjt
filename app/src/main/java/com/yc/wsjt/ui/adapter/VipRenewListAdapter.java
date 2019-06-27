package com.yc.wsjt.ui.adapter;

import android.content.Context;

import com.blankj.utilcode.util.SizeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.VipInfo;

import java.util.List;


public class VipRenewListAdapter extends BaseQuickAdapter<VipInfo, BaseViewHolder> {

    private Context mContext;

    private int tempWidth = SizeUtils.dp2px(290) / 3;

    public VipRenewListAdapter(Context context, List<VipInfo> datas) {
        super(R.layout.vip_renew_item_view, datas);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder holder, VipInfo vipInfo) {
        holder.setText(R.id.tv_vip_info, vipInfo.getPrice() + "元/" + vipInfo.getName());
        holder.addOnClickListener(R.id.btn_renew);
    }
}