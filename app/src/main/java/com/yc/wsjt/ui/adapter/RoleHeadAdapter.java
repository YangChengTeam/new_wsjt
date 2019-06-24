package com.yc.wsjt.ui.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.RoleInfo;

import java.util.List;


public class RoleHeadAdapter extends BaseQuickAdapter<RoleInfo, BaseViewHolder> {

    private Context mContext;

    private int mWidth;

    public RoleHeadAdapter(Context context, List<RoleInfo> datas, int width) {
        super(R.layout.role_head_item, datas);
        this.mContext = context;
        this.mWidth = width;
    }

    @Override
    protected void convert(BaseViewHolder holder, RoleInfo roleInfo) {
        LinearLayout roleHeadLayout = holder.getView(R.id.layout_role_head);
        roleHeadLayout.setLayoutParams(new LinearLayout.LayoutParams(mWidth, mWidth));

        if (StringUtils.isEmpty(roleInfo.getAvatar())) {
            Glide.with(mContext).load(roleInfo.getAvatarLocalImg()).into((ImageView) holder.getView(R.id.iv_role_head));
        } else {
            Glide.with(mContext).load(roleInfo.getAvatar()).into((ImageView) holder.getView(R.id.iv_role_head));
        }
    }
}