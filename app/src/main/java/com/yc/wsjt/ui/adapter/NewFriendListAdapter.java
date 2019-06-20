package com.yc.wsjt.ui.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.FriendInfo;

import java.util.List;


public class NewFriendListAdapter extends BaseQuickAdapter<FriendInfo, BaseViewHolder> {

    private Context mContext;

    public NewFriendListAdapter(Context context, List<FriendInfo> datas) {
        super(R.layout.new_friend_item, datas);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder holder, FriendInfo friendInfo) {
        if (StringUtils.isEmpty(friendInfo.getUserHead())) {
            Glide.with(mContext).load(friendInfo.getLocalHead()).into((ImageView) holder.getView(R.id.iv_user_head));
        } else {
            Glide.with(mContext).load(friendInfo.getUserHead()).into((ImageView) holder.getView(R.id.iv_user_head));
        }
        holder.setText(R.id.tv_user_name, friendInfo.getNickName())
                .setText(R.id.tv_user_desc, friendInfo.getRemark());
        holder.addOnClickListener(R.id.layout_accept);

        LinearLayout acceptLayout = holder.getView(R.id.layout_accept);
        TextView acceptTxt = holder.getView(R.id.tv_accept_txt);
        if (friendInfo.isReceive()) {
            acceptLayout.setBackground(null);
            acceptTxt.setTextColor(ContextCompat.getColor(mContext, R.color.gray3));
            acceptTxt.setText("已添加");
        } else {
            acceptLayout.setBackgroundResource(R.drawable.accept_normal);
            acceptTxt.setTextColor(ContextCompat.getColor(mContext, R.color.into_money_color));
            acceptTxt.setText("接受");
        }

    }
}