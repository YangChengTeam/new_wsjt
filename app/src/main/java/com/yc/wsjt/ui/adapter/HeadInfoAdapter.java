package com.yc.wsjt.ui.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.yc.wsjt.R;
import com.yc.wsjt.bean.HeadInfo;
import com.yc.wsjt.common.Constants;
import com.yc.wsjt.ui.custom.GlideRoundTransform;
import com.blankj.utilcode.util.SizeUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.orhanobut.logger.Logger;

import java.util.List;


public class HeadInfoAdapter extends BaseQuickAdapter<HeadInfo, BaseViewHolder> {

    private Context mContext;

    private int tempWidth;

    public HeadInfoAdapter(Context context, List<HeadInfo> datas, int tempWidth) {
        super(R.layout.head_item_view, datas);
        this.mContext = context;
        this.tempWidth = tempWidth;
    }

    @Override
    protected void convert(BaseViewHolder holder, HeadInfo gifInfo) {

        Logger.i("head url --->" + gifInfo.getHeadUrl());

        LinearLayout.LayoutParams itemParams = new LinearLayout.LayoutParams(tempWidth, tempWidth);
        holder.getView(R.id.layout_head_item).setLayoutParams(itemParams);
        RequestOptions options = new RequestOptions();
        options.placeholder(R.mipmap.image_def)
                .override(tempWidth - SizeUtils.dp2px(3), tempWidth - SizeUtils.dp2px(3))
                .transform(new GlideRoundTransform(mContext, 6));

        Glide.with(mContext)
                .load(Constants.BASE_IMAGE_URL + gifInfo.getHeadUrl())
                .apply(options)
                .into((ImageView) holder.getView(R.id.iv_head));
    }
}