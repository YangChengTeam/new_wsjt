package com.yc.wsjt.ui.adapter;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.CommentInfo;
import com.yc.wsjt.ui.custom.MyClickText;

import java.util.List;


public class CommentListAdapter extends BaseQuickAdapter<CommentInfo, BaseViewHolder> {

    private Context mContext;

    public CommentListAdapter(Context context, List<CommentInfo> datas) {
        super(R.layout.comment_item, datas);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder holder, CommentInfo commentInfo) {
        String sendUserName = "周杰伦";
        String replyUserName = "昆凌";
        String content = ":你今天真的很漂亮啊你今天真的很漂亮啊";
        SpannableString tempStr = new SpannableString(sendUserName + "回复" + replyUserName + content);
        tempStr.setSpan(new MyClickText(mContext), 0, sendUserName.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tempStr.setSpan(new MyClickText(mContext), tempStr.toString().indexOf(replyUserName),tempStr.toString().indexOf(replyUserName) + replyUserName.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //当然这里也可以通过setSpan来设置哪些位置的文本哪些颜色
        holder.setText(R.id.tv_comment_info,tempStr);
    }
}