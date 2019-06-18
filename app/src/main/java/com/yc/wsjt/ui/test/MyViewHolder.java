package com.yc.wsjt.ui.test;

import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.yc.wsjt.R;


/**
 * Created by wujingchao92@gmail.com on 2016/6/19.
 */
class MyViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView;

    public MyViewHolder(View itemView) {
        super(itemView);
        imageView = (ImageView) itemView.findViewById(R.id.iv);
    }

}
