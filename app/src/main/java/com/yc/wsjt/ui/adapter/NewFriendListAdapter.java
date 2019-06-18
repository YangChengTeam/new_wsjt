package com.yc.wsjt.ui.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.NewFriendInfo;

import java.util.List;


public class NewFriendListAdapter extends BaseQuickAdapter<NewFriendInfo, BaseViewHolder> {

    private Context mContext;

    public NewFriendListAdapter(Context context, List<NewFriendInfo> datas) {
        super(R.layout.new_friend_item, datas);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder holder, NewFriendInfo newFriendInfo) {
        holder.setText(R.id.tv_user_name,newFriendInfo.getUserName())
                .setText(R.id.tv_user_desc,newFriendInfo.getUserDesc());
    }
}