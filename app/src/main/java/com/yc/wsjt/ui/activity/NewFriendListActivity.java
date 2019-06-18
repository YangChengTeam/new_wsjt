package com.yc.wsjt.ui.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jaeger.library.StatusBarUtil;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.NewFriendInfo;
import com.yc.wsjt.presenter.Presenter;
import com.yc.wsjt.ui.adapter.NewFriendListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhangdinghui on 2019/5/27.
 */
public class NewFriendListActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView mBackIv;

    @BindView(R.id.tv_title)
    TextView mTitleTv;

    @BindView(R.id.tv_setting)
    TextView mAddFriendTv;

    @BindView(R.id.new_friend_list)
    RecyclerView mNewFriendListView;

    NewFriendListAdapter newFriendListAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_new_friend_list;
    }

    @Override
    protected Presenter getPresenter() {
        return null;
    }

    @Override
    protected void initVars() {
        StatusBarUtil.setLightMode(this);
    }

    @Override
    protected void initViews() {
        mTitleTv.setText("新的朋友");
        mAddFriendTv.setText("添加朋友");
        mAddFriendTv.setTextColor(ContextCompat.getColor(this, R.color.black1));

        List<NewFriendInfo> list = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            NewFriendInfo newFriendInfo = new NewFriendInfo();
            newFriendInfo.setUserName("测试用户" + i);
            newFriendInfo.setUserDesc("测试描述信息" + i);
            list.add(newFriendInfo);
        }

        newFriendListAdapter = new NewFriendListAdapter(this, list);
        mNewFriendListView.setLayoutManager(new LinearLayoutManager(this));
        mNewFriendListView.setAdapter(newFriendListAdapter);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @OnClick(R.id.iv_back)
    void back() {
        finish();
    }
}
