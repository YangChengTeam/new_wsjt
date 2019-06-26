package com.yc.wsjt.ui.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.wsjt.R;

import java.util.List;


public class CircleImageAdapter extends BaseQuickAdapter<Object, BaseViewHolder> {

    private Context mContext;

    private int tempWidth;

    public CircleImageAdapter(Context context, List<Object> datas) {
        super(R.layout.circle_image_item_view, datas);
        this.mContext = context;
        this.tempWidth = (ScreenUtils.getScreenWidth() - SizeUtils.dp2px(24)) / 5;
    }

    @Override
    protected void convert(BaseViewHolder holder, Object circleUrl) {

        LinearLayout linearLayout = holder.getView(R.id.layout_circle_item);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(tempWidth, tempWidth);
        linearLayout.setLayoutParams(params);

        RequestOptions options = new RequestOptions();
        options.override(tempWidth - SizeUtils.dp2px(6), tempWidth - SizeUtils.dp2px(6));
        Glide.with(mContext).load(circleUrl).apply(options).into((ImageView) holder.getView(R.id.iv_circle_image));
    }
}