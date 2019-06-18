package com.yc.wsjt.ui.adapter;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.CircleInfo;
import com.yc.wsjt.bean.CommentInfo;
import com.yc.wsjt.ui.custom.FriendsCircleImageLayout;

import java.util.ArrayList;
import java.util.List;


public class CircleListAdapter extends BaseQuickAdapter<CircleInfo, BaseViewHolder> {

    private Context mContext;

    private String mImageUrl = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1559280363073&di=4124de8dc5fc3b6929541fe6384e26fe&imgtype=0&src=http%3A%2F%2Fwww.xnnews.com.cn%2Fwenyu%2Flxsj%2F201709%2FW020170928755316878313.jpg";

    //
    public CircleListAdapter(Context context, List<CircleInfo> datas) {
        super(R.layout.circle_item, datas);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder holder, CircleInfo circleInfo) {
        List<String> imageUrls = new ArrayList<>();
        int count = holder.getAdapterPosition() % 10;
        for (int i = 0; i < count; i++) {
            imageUrls.add(mImageUrl);
        }
        FriendsCircleImageLayout imageLayout = holder.getView(R.id.image_list);
        imageLayout.setImageUrls(imageUrls);
        List<CommentInfo> list = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            list.add(new CommentInfo());
        }
        RecyclerView commentListView = holder.getView(R.id.comment_list);
        CommentListAdapter adapter = new CommentListAdapter(mContext, list);
        commentListView.setLayoutManager(new LinearLayoutManager(mContext));
        commentListView.setAdapter(adapter);
    }
}