package com.yc.wsjt.ui.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.ScreenUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.wsjt.R;

import java.util.List;


public class GroupSetItemAdapter extends BaseQuickAdapter<Integer, BaseViewHolder> {

    private Context mContext;

    private int tempWidth;

    public GroupSetItemAdapter(Context context, List<Integer> datas) {
        super(R.layout.group_set_item, datas);
        this.mContext = context;
        this.tempWidth = ScreenUtils.getScreenWidth() / 5;
    }

    @Override
    protected void convert(BaseViewHolder holder, Integer groupItem) {
        LinearLayout itemLayout = holder.getView(R.id.layout_item);
        itemLayout.setLayoutParams(new LinearLayout.LayoutParams(tempWidth,tempWidth));

        Glide.with(mContext).load(groupItem).into((ImageView) holder.getView(R.id.iv_group_item_head));
    }
}