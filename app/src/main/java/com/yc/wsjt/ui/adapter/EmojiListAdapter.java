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
import com.yc.wsjt.bean.EmojiInfo;
import com.yc.wsjt.common.Constants;

import java.util.List;


public class EmojiListAdapter extends BaseQuickAdapter<EmojiInfo, BaseViewHolder> {

    private Context mContext;

    private int tempWidth;

    public EmojiListAdapter(Context context, List<EmojiInfo> datas) {
        super(R.layout.emoji_item_view, datas);
        this.mContext = context;
        this.tempWidth = ScreenUtils.getScreenWidth() / 4;
    }

    @Override
    protected void convert(BaseViewHolder holder, EmojiInfo emojiInfo) {

        LinearLayout linearLayout = holder.getView(R.id.layout_item_emoji);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(tempWidth, tempWidth);
        linearLayout.setLayoutParams(params);

        RequestOptions options = new RequestOptions();
        options.override(tempWidth - SizeUtils.dp2px(8), tempWidth - SizeUtils.dp2px(8));
        Glide.with(mContext).load(Constants.BASE_IMAGE_URL + emojiInfo.getIcon()).apply(options).into((ImageView) holder.getView(R.id.iv_emoji));
    }
}