package com.yc.wsjt.ui.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.wsjt.R;

import java.util.List;


public class BillListAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private Context mContext;

    public BillListAdapter(Context context, List<String> datas) {
        super(R.layout.bill_item_view, datas);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder holder, String bill) {
    }
}