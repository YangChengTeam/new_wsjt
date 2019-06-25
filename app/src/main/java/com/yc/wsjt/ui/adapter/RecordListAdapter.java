package com.yc.wsjt.ui.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.RedRecordInfo;

import java.util.List;


public class RecordListAdapter extends BaseQuickAdapter<RedRecordInfo, BaseViewHolder> {

    private Context mContext;

    public RecordListAdapter(Context context, List<RedRecordInfo> datas) {
        super(R.layout.red_record_item, datas);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder holder, RedRecordInfo redRecordInfo) {
        holder.setText(R.id.tv_user_name, redRecordInfo.getUserName())
        .setText(R.id.tv_red_amount,redRecordInfo.getRedAmount())
        .setText(R.id.tv_receive_date,redRecordInfo.getReceiveDate());
        Glide.with(mContext).load(redRecordInfo.getUserHead()).into((ImageView) holder.getView(R.id.iv_user_head));
    }
}