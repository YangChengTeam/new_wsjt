package com.yc.wsjt.ui.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.CircleInfo;

import java.util.List;


public class CircleInfoListAdapter extends BaseQuickAdapter<CircleInfo, BaseViewHolder> {

    private Context mContext;

    public CircleInfoListAdapter(Context context, List<CircleInfo> datas) {
        super(R.layout.circle_info_item, datas);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder holder, CircleInfo circleInfo) {
        holder.setText(R.id.tv_send_user_name,circleInfo.getUserName());
        holder.setText(R.id.tv_send_content,circleInfo.getContent());
        Glide.with(mContext).load(circleInfo.getUserHead()).into((ImageView) holder.getView(R.id.iv_send_user_head));
    }
}