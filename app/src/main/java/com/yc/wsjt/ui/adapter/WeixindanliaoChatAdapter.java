package com.yc.wsjt.ui.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.WeixinChatInfo;
import com.yc.wsjt.common.Constants;

import java.util.List;


public class WeixindanliaoChatAdapter extends BaseQuickAdapter<WeixinChatInfo, BaseViewHolder> {

    private Context mContext;

    public WeixindanliaoChatAdapter(Context context, List<WeixinChatInfo> datas, int tempWidth) {
        super(R.layout.weixin_danliao_chat_item, datas);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder holder, WeixinChatInfo weixinChatInfo) {
        String tempType = "[ " + mContext.getResources().getString(Constants.chatTypeNames[weixinChatInfo.getType()]) + " ]";
        Glide.with(mContext).load(weixinChatInfo.getTypeIcon()).into((ImageView) holder.getView(R.id.iv_type_icon));
        holder.setText(R.id.tv_type, tempType);
        holder.setText(R.id.tv_chat_text, weixinChatInfo.getChatText());
        holder.addOnClickListener(R.id.btn_delete);
    }
}