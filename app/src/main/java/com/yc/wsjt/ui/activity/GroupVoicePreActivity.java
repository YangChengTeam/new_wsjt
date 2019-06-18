package com.yc.wsjt.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.SizeUtils;
import com.yc.wsjt.R;
import com.yc.wsjt.ui.adapter.GroupVoiceHeadAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhangdinghui on 2019/6/3.
 */
public class GroupVoicePreActivity extends Activity{

    @BindView(R.id.chat_group_head_list)
    RecyclerView mChatGroupListView;

    GroupVoiceHeadAdapter groupVoiceHeadAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //无title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_group_voice_show);
        ButterKnife.bind(this);
        initViews();
    }

    public void initViews() {


        List<Integer> list = new ArrayList<>();
        list.add(R.mipmap.c1);
        list.add(R.mipmap.c2);
        //list.add(R.mipmap.c3);
        //list.add(R.mipmap.c4);
        groupVoiceHeadAdapter = new GroupVoiceHeadAdapter(this, list);
        mChatGroupListView.setLayoutManager(new GridLayoutManager(this, 2));
        mChatGroupListView.setAdapter(groupVoiceHeadAdapter);
        if (list.size() < 3) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, SizeUtils.dp2px(60), 0, 0);
            mChatGroupListView.setLayoutParams(params);
        }
    }

}
