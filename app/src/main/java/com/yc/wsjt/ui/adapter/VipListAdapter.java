package com.yc.wsjt.ui.adapter;

import android.content.Context;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.SizeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.VipInfo;

import java.util.List;


public class VipListAdapter extends BaseQuickAdapter<VipInfo, BaseViewHolder> {

    private Context mContext;

    private int tempWidth = SizeUtils.dp2px(290) / 3;

    public VipListAdapter(Context context, List<VipInfo> datas) {
        super(R.layout.vip_item_view, datas);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder holder, VipInfo vipInfo) {
        holder.setText(R.id.tv_vip_name, vipInfo.getName())
                .setText(R.id.tv_vip_price, vipInfo.getPrice() + "元");

        LinearLayout vipLayout = holder.getView(R.id.layout_vip_item);
        vipLayout.setLayoutParams(new LinearLayout.LayoutParams(tempWidth, LinearLayout.LayoutParams.WRAP_CONTENT));

        if(vipInfo.isChecked()){
            holder.setBackgroundRes(R.id.layout_vip,R.drawable.feed_selected);
            holder.setTextColor(R.id.tv_vip_name, ContextCompat.getColor(mContext,R.color.feed_line_select_color));
            holder.setTextColor(R.id.tv_vip_price, ContextCompat.getColor(mContext,R.color.feed_line_select_color));
        }else{
            holder.setBackgroundRes(R.id.layout_vip,R.drawable.feed_normal);
            holder.setTextColor(R.id.tv_vip_name, ContextCompat.getColor(mContext,R.color.tab_tv_normal));
            holder.setTextColor(R.id.tv_vip_price, ContextCompat.getColor(mContext,R.color.tab_tv_normal));
        }

    }
}