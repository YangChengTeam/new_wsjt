package com.yc.wsjt.ui.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.wsjt.R;

import java.util.List;


public class WeiXinHomeListAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private Context mContext;

    public WeiXinHomeListAdapter(Context context, List<String> datas) {
        super(R.layout.weixin_home_item, datas);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder holder, String bill) {
    }
}