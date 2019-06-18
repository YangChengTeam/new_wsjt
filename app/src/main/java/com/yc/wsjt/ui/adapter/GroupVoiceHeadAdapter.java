package com.yc.wsjt.ui.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.ScreenUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.wsjt.R;

import java.util.List;


public class GroupVoiceHeadAdapter extends BaseQuickAdapter<Integer, BaseViewHolder> {

    private Context mContext;

    private int tempWidth;

    public GroupVoiceHeadAdapter(Context context, List<Integer> datas) {
        super(R.layout.group_voice_item, datas);
        this.mContext = context;
        this.tempWidth = ScreenUtils.getScreenWidth() / 2;
    }

    @Override
    protected void convert(BaseViewHolder holder, Integer voiceHead) {
        LinearLayout voiceHeadLayout = holder.getView(R.id.layout_voice_head);
        voiceHeadLayout.setLayoutParams(new LinearLayout.LayoutParams(tempWidth, tempWidth));
        RequestOptions options = new RequestOptions();
        options.override(tempWidth, tempWidth);
        Glide.with(mContext).load(voiceHead).apply(options).into((ImageView) holder.getView(R.id.iv_group_voice));
    }
}