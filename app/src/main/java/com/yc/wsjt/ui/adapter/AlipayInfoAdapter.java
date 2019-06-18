package com.yc.wsjt.ui.adapter;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.QuickInfo;
import com.yc.wsjt.common.Constants;

import java.util.List;


public class AlipayInfoAdapter extends BaseQuickAdapter<QuickInfo, BaseViewHolder> {

    private Context mContext;

    private int tempWidth;

    private boolean isShowAdd;

    public AlipayInfoAdapter(Context context, List<QuickInfo> datas, int tempWidth, boolean isShow) {
        super(R.layout.quick_alipay_item, datas);
        this.mContext = context;
        this.tempWidth = tempWidth;
        this.isShowAdd = isShow;
    }

    @Override
    protected void convert(BaseViewHolder holder, QuickInfo quickInfo) {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, tempWidth);
        FrameLayout itemLayout = holder.getView(R.id.layout_item_model);
        itemLayout.setLayoutParams(params);

        holder.setText(R.id.tv_weixin_info_name, quickInfo.getName());
        Glide.with(mContext).load(Constants.BASE_IMAGE_URL + quickInfo.getIcon()).into((ImageView) holder.getView(R.id.iv_quick));
        holder.addOnClickListener(R.id.iv_add_quick);
    }
}