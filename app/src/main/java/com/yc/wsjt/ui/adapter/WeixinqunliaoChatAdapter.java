package com.yc.wsjt.ui.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.WeixinQunChatInfo;
import com.yc.wsjt.common.Constants;

import java.util.List;


public class WeixinqunliaoChatAdapter extends BaseQuickAdapter<WeixinQunChatInfo, BaseViewHolder> {

    private Context mContext;

    public WeixinqunliaoChatAdapter(Context context, List<WeixinQunChatInfo> datas, int tempWidth) {
        super(R.layout.weixin_danliao_chat_item, datas);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder holder, WeixinQunChatInfo weixinQunChatInfo) {
        String tempType = "[ " + mContext.getResources().getString(Constants.qunNames[weixinQunChatInfo.getType()]) + " ]";
        Glide.with(mContext).load(weixinQunChatInfo.getTypeIcon()).into((ImageView) holder.getView(R.id.iv_type_icon));
        holder.setText(R.id.tv_type, tempType);
        holder.setText(R.id.tv_chat_text, weixinQunChatInfo.getChatText());
        holder.addOnClickListener(R.id.btn_delete);
    }
}