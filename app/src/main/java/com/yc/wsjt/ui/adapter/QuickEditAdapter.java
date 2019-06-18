package com.yc.wsjt.ui.adapter;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.QuickInfo;
import com.yc.wsjt.common.Constants;

import java.util.List;


public class QuickEditAdapter extends BaseQuickAdapter<QuickInfo, BaseViewHolder> {

    private Context mContext;

    private int tempWidth;

    private boolean isEdit = true;

    public void setEdit(boolean edit) {
        isEdit = edit;
    }

    public QuickEditAdapter(Context context, List<QuickInfo> datas, int tempWidth) {
        super(R.layout.quick_edit_item, datas);
        this.mContext = context;
        this.tempWidth = tempWidth;
    }

    @Override
    protected void convert(BaseViewHolder holder, QuickInfo quickInfo) {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, tempWidth);
        FrameLayout itemLayout = holder.getView(R.id.layout_item_model);
        itemLayout.setLayoutParams(params);

        if (!isEdit) {
            //holder.setVisible(R.id.tv_quick_name, false);
            //holder.setVisible(R.id.iv_quick, false);
            holder.setVisible(R.id.iv_quick_delete, false);
        } else {
            holder.setVisible(R.id.tv_quick_name, true);
            holder.setVisible(R.id.iv_quick, true);
            holder.setVisible(R.id.iv_quick_delete, true);
            holder.setText(R.id.tv_quick_name, quickInfo.getName());
            Glide.with(mContext).load(Constants.BASE_IMAGE_URL + quickInfo.getIcon()).into((ImageView) holder.getView(R.id.iv_quick));
        }

        holder.addOnClickListener(R.id.iv_quick_delete);
    }
}