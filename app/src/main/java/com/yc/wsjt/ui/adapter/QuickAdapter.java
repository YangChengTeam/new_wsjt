package com.yc.wsjt.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.QuickInfo;
import com.yc.wsjt.common.Constants;

import java.util.List;


public class QuickAdapter extends BaseQuickAdapter<QuickInfo, BaseViewHolder> {

    private Context mContext;

    private int tempWidth;

    private int marginSize;

    public QuickAdapter(Context context, List<QuickInfo> datas, int tempWidth) {
        super(R.layout.quick_item, datas);
        this.mContext = context;
        this.tempWidth = tempWidth;
        this.marginSize = SizeUtils.dp2px(5);
    }

    @Override
    protected void convert(BaseViewHolder holder, QuickInfo quickInfo) {
        TextView quickName = holder.getView(R.id.tv_quick_name);

        if (StringUtils.isEmpty(quickInfo.getIcon())) {
            Glide.with(mContext).load(quickInfo.getLocalImg()).into((ImageView) holder.getView(R.id.iv_quick));
            quickName.setVisibility(View.GONE);
        } else {
            quickName.setVisibility(View.VISIBLE);
            quickName.setText(quickInfo.getName());
            Glide.with(mContext).load(Constants.BASE_IMAGE_URL + quickInfo.getIcon()).into((ImageView) holder.getView(R.id.iv_quick));
        }
    }
}