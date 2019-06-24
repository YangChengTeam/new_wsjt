package com.yc.wsjt.ui.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.RoleInfo;

import java.util.List;


public class RoleListAdapter extends BaseQuickAdapter<RoleInfo, BaseViewHolder> {

    private Context mContext;

    public RoleListAdapter(Context context, List<RoleInfo> datas) {
        super(R.layout.role_select_item, datas);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder holder, RoleInfo roleInfo) {
        holder.setText(R.id.tv_role_name, holder.getAdapterPosition() == 0 ? roleInfo.getNickname() + "(自己)" : roleInfo.getNickname());
        if (StringUtils.isEmpty(roleInfo.getAvatar())) {
            Glide.with(mContext).load(roleInfo.getAvatarLocalImg()).into((ImageView) holder.getView(R.id.iv_role_head));
        } else {
            Glide.with(mContext).load(roleInfo.getAvatar()).into((ImageView) holder.getView(R.id.iv_role_head));
        }
    }
}