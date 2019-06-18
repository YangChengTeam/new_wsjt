package com.yc.wsjt.ui.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.yc.wsjt.R;
import com.yc.wsjt.bean.HeadInfo;
import com.yc.wsjt.common.Constants;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;


public class AvatarShowAdapter extends BaseQuickAdapter<HeadInfo, BaseViewHolder> {

    private Context mContext;

    private int tempWidth;

    public AvatarShowAdapter(Context context, List<HeadInfo> datas, int tempWidth) {
        super(R.layout.avatar_item, datas);
        this.mContext = context;
        this.tempWidth = tempWidth;
    }

    @Override
    protected void convert(BaseViewHolder holder, HeadInfo headInfo) {
        RequestOptions options = new RequestOptions();
        options.override(tempWidth, tempWidth);

        Glide.with(mContext).load(Constants.BASE_IMAGE_URL + headInfo.getHeadUrl()).apply(options).into((ImageView) holder.getView(R.id.iv_avatar));
    }
}