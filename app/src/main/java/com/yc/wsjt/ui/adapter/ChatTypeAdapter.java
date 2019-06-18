package com.yc.wsjt.ui.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.SizeUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.ChatTypeInfo;
import com.yc.wsjt.bean.QuickInfo;

import java.util.List;


public class ChatTypeAdapter extends BaseQuickAdapter<ChatTypeInfo, BaseViewHolder> {

    private Context mContext;

    private int tempWidth;

    private int marginSize;

    public ChatTypeAdapter(Context context, List<ChatTypeInfo> datas, int tempWidth) {
        super(R.layout.chat_type_item, datas);
        this.mContext = context;
        this.tempWidth = tempWidth;
        this.marginSize = SizeUtils.dp2px(5);
    }

    @Override
    protected void convert(BaseViewHolder holder, ChatTypeInfo chatTypeInfo) {
        LinearLayout itemLayout = holder.getView(R.id.layout_item_type);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(tempWidth, tempWidth);
        itemLayout.setLayoutParams(params);
        holder.setText(R.id.tv_type_name, chatTypeInfo.getTypeName());
        Glide.with(mContext).load(chatTypeInfo.getTypeImg()).into((ImageView) holder.getView(R.id.iv_type));
    }
}