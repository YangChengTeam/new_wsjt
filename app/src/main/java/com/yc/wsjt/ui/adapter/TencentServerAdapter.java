package com.yc.wsjt.ui.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.QuickInfo;

import java.util.List;


public class TencentServerAdapter extends BaseQuickAdapter<QuickInfo, BaseViewHolder> {

    private Context mContext;

    private int tempWidth;

    public TencentServerAdapter(Context context, List<QuickInfo> datas, int tempWidth) {
        super(R.layout.tencent_server_item, datas);
        this.mContext = context;
        this.tempWidth = tempWidth;
    }

    @Override
    protected void convert(BaseViewHolder holder, QuickInfo quickInfo) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, tempWidth);
        LinearLayout itemLayout = holder.getView(R.id.layout_item_model);
        itemLayout.setLayoutParams(params);
        Glide.with(mContext).load(quickInfo.getLocalImg()).into((ImageView) holder.getView(R.id.iv_server));
        holder.setText(R.id.tv_weixin_info_name, quickInfo.getName());
    }
}