package com.yc.wsjt.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.AppBarLayout;
import com.jaeger.library.StatusBarUtil;
import com.orhanobut.logger.Logger;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.CircleInfo;
import com.yc.wsjt.presenter.Presenter;
import com.yc.wsjt.ui.adapter.CircleListAdapter;
import com.yc.wsjt.ui.custom.AppBarStateChangeListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by zhangdinghui on 2019/5/30.
 */
public class WeiXinCircleActivity extends BaseActivity {

    @BindView(R.id.appbar)
    AppBarLayout appBarLayout;

    @BindView(R.id.iv_back)
    ImageView mBackIv;

    @BindView(R.id.tv_circle_title)
    TextView mTitleTv;

    @BindView(R.id.iv_take_phone)
    ImageView mTakePhoneIv;

    @BindView(R.id.circle_list)
    RecyclerView circleListView;

    CircleListAdapter circleListAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_circle;
    }

    @Override
    protected Presenter getPresenter() {
        return null;
    }

    @Override
    protected void initVars() {
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.transparent), 0);
        StatusBarUtil.setDarkMode(this);
    }

    @Override
    protected void initViews() {
        appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                Logger.i("STATE", state.name());
                if (state == State.EXPANDED) {
                    Logger.i("展开--->");
                    changeState(true);
                } else if (state == State.COLLAPSED) {
                    Logger.i("折叠--->");
                    changeState(false);
                } else {
                    //中间状态
                }
            }
        });

        List<CircleInfo> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new CircleInfo());
        }

        circleListAdapter = new CircleListAdapter(this, list);
        circleListView.setLayoutManager(new LinearLayoutManager(this));
        circleListView.setAdapter(circleListAdapter);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    public void changeState(boolean expand) {
        if (expand) {
            mBackIv.setImageResource(R.mipmap.pyq_white_back);
            mTitleTv.setVisibility(View.GONE);
            mTakePhoneIv.setImageResource(R.mipmap.wechat_phone_white);
            StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.transparent), 0);
            StatusBarUtil.setDarkMode(this);
        } else {
            mBackIv.setImageResource(R.mipmap.pyq_black_back);
            mTitleTv.setVisibility(View.VISIBLE);
            mTakePhoneIv.setImageResource(R.mipmap.wechat_phone_black);
            StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.get_money_color), 0);
            StatusBarUtil.setLightMode(this);
        }
    }

}
