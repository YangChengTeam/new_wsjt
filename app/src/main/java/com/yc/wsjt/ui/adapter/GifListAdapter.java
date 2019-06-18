package com.yc.wsjt.ui.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.yc.wsjt.R;
import com.yc.wsjt.bean.GifInfo;
import com.yc.wsjt.common.Constants;
import com.yc.wsjt.ui.custom.GlideRoundTransform;
import com.blankj.utilcode.util.SizeUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;


public class GifListAdapter extends BaseQuickAdapter<GifInfo, BaseViewHolder> {

    private Context mContext;

    private int tempWidth;

    public GifListAdapter(Context context, List<GifInfo> datas, int tempWidth) {
        super(R.layout.gif_item_view, datas);
        this.mContext = context;
        this.tempWidth = tempWidth;
    }

    @Override
    protected void convert(BaseViewHolder holder, GifInfo gifInfo) {
        LinearLayout.LayoutParams itemParams = new LinearLayout.LayoutParams(tempWidth, tempWidth);
        holder.getView(R.id.layout_gif_item).setLayoutParams(itemParams);
        RequestOptions options = new RequestOptions();
        options.placeholder(R.mipmap.image_def)
                .override(tempWidth - SizeUtils.dp2px(8), tempWidth - SizeUtils.dp2px(8))
                .transform(new GlideRoundTransform(mContext, 6));

        Glide.with(mContext)
                .load(Constants.LOCAL_GIF_URL + gifInfo.getGifUrl())
                .apply(options)
                .into((ImageView) holder.getView(R.id.iv_gif));
    }
}