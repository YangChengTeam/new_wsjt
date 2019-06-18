package com.yc.wsjt.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jaeger.library.StatusBarUtil;
import com.yc.wsjt.R;
import com.yc.wsjt.presenter.Presenter;
import com.yc.wsjt.ui.adapter.GroupSetItemAdapter;
import com.yc.wsjt.ui.custom.VideoTimeDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhangdinghui on 2019/5/8.
 * 群语音聊天设置
 */
public class GroupVoiceSetActivity extends BaseActivity implements VideoTimeDialog.DateSelectListener {

    @BindView(R.id.tv_title)
    TextView mTitleTv;

    @BindView(R.id.btn_config)
    Button mConfigBtn;

    @BindView(R.id.group_head_list)
    RecyclerView groupHeadListView;

    @BindView(R.id.tv_voice_time)
    TextView mVoiceTimeTv;

    GroupSetItemAdapter groupSetItemAdapter;

    VideoTimeDialog videoTimeDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_group_voice_set;
    }

    @Override
    protected Presenter getPresenter() {
        return null;
    }

    @Override
    protected void initVars() {
        StatusBarUtil.setLightMode(this);
        mConfigBtn.setVisibility(View.GONE);
        mTitleTv.setText("群语音聊天");
        videoTimeDialog = new VideoTimeDialog(this, R.style.video_time_dialog, 10);
        videoTimeDialog.setDateSelectListener(this);
    }

    @Override
    protected void initViews() {
        List<Integer> list = new ArrayList<>();
        list.add(R.mipmap.c1);
        list.add(R.mipmap.c2);
        list.add(R.mipmap.add_icon);

        groupSetItemAdapter = new GroupSetItemAdapter(this, list);
        groupHeadListView.setLayoutManager(new GridLayoutManager(this, 5));
        groupHeadListView.setAdapter(groupSetItemAdapter);
        groupSetItemAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(GroupVoiceSetActivity.this, AddGroupUserActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @OnClick(R.id.layout_voice_time)
    void setVoiceTime() {
        videoTimeDialog.show();

        WindowManager.LayoutParams windowParams = videoTimeDialog.getWindow().getAttributes();
        windowParams.width = (int) (ScreenUtils.getScreenWidth() * 0.7);
        windowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        videoTimeDialog.getWindow().setAttributes(windowParams);
    }

    @Override
    public void configDate(String selectDate) {
        if (!StringUtils.isEmpty(selectDate)) {
            mVoiceTimeTv.setText(selectDate);
        }
    }

    @OnClick(R.id.btn_pre_show)
    void preShow() {
        Intent intent = new Intent(this, GroupVoicePreActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.iv_back)
    void back() {
        finish();
    }
}
