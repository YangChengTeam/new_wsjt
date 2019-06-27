package com.yc.wsjt.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.CircleInfo;
import com.yc.wsjt.bean.CommentInfo;
import com.yc.wsjt.ui.custom.FriendsCircleImageLayout;
import com.yc.wsjt.ui.custom.RoundedCornersTransformation;

import java.util.ArrayList;
import java.util.List;


public class CircleListAdapter extends BaseQuickAdapter<CircleInfo, BaseViewHolder> {

    private Context mContext;

    public CircleListAdapter(Context context, List<CircleInfo> datas) {
        super(R.layout.circle_item, datas);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder holder, CircleInfo circleInfo) {

        RequestOptions options = new RequestOptions();
        options.transform(new RoundedCornersTransformation(SizeUtils.dp2px(3), 0));
        Glide.with(mContext).load(circleInfo.getUserHead()).apply(options).into((ImageView) holder.getView(R.id.iv_user_head));

        holder.setText(R.id.tv_user_name, circleInfo.getUserName())
                .setText(R.id.tv_circle_content, circleInfo.getContent())
                .setText(R.id.tv_praise_info, "     " + circleInfo.getPraiseInfo());

        if (StringUtils.isEmpty(circleInfo.getAddress())) {
            holder.setVisible(R.id.layout_address, false);
        } else {
            holder.setVisible(R.id.layout_address, true);
            holder.setText(R.id.tv_address, circleInfo.getAddress());
        }

        //点赞区域
        FrameLayout praiseLayout = holder.getView(R.id.layout_praise);
        if (StringUtils.isEmpty(circleInfo.getPraiseInfo())) {
            praiseLayout.setVisibility(View.GONE);
            holder.setVisible(R.id.comment_line, false);
        } else {
            praiseLayout.setVisibility(View.VISIBLE);
            holder.setVisible(R.id.comment_line, true);
        }

        //图片区域
        List<String> imagesList = new ArrayList<>();
        if (!StringUtils.isEmpty(circleInfo.getCircleImages())) {
            String[] images = circleInfo.getCircleImages().split("#");
            for (int i = 0; i < images.length; i++) {
                imagesList.add(images[i]);
            }
        }
        FriendsCircleImageLayout imageLayout = holder.getView(R.id.image_list);
        if (imagesList == null || imagesList.size() == 0) {
            imageLayout.setVisibility(View.GONE);
        } else {
            imageLayout.setVisibility(View.VISIBLE);
            imageLayout.setImageUrls(imagesList);
        }

        //评论列表区域
        RecyclerView commentListView = holder.getView(R.id.comment_list);
        List<CommentInfo> list = circleInfo.getCommentInfos();
        if (list == null || list.size() == 0) {
            commentListView.setVisibility(View.GONE);
        } else {
            commentListView.setVisibility(View.VISIBLE);
            CommentListAdapter adapter = new CommentListAdapter(mContext, list);
            commentListView.setLayoutManager(new LinearLayoutManager(mContext));
            commentListView.setAdapter(adapter);
        }

        if(commentListView.getVisibility() == View.GONE && praiseLayout.getVisibility() == View.GONE){
            holder.setVisible(R.id.layout_praise_comment,false);
        }

        holder.addOnClickListener(R.id.iv_moments);
    }
}