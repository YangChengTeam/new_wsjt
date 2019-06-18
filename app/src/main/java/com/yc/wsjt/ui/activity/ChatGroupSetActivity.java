package com.yc.wsjt.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jaeger.library.StatusBarUtil;
import com.yc.wsjt.R;
import com.yc.wsjt.presenter.Presenter;
import com.yc.wsjt.ui.adapter.GroupSetItemAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhangdinghui on 2019/5/8.
 */
public class ChatGroupSetActivity extends BaseActivity {

    @BindView(R.id.btn_config)
    Button mConfigBtn;

    @BindView(R.id.group_head_list)
    RecyclerView groupHeadListView;

    GroupSetItemAdapter groupSetItemAdapter;

    @BindView(R.id.btn_random)
    Button mRandomBtn;

    public int[] groups = {R.mipmap.c1, R.mipmap.c2, R.mipmap.c3, R.mipmap.c4, R.mipmap.c5, R.mipmap.c6, R.mipmap.c7, R.mipmap.c8, R.mipmap.c9};

    @Override
    protected int getLayoutId() {
        return R.layout.activity_chat_group_set;
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
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < groups.length; i++) {
            list.add(groups[i]);
        }

        groupSetItemAdapter = new GroupSetItemAdapter(this, list);
        groupHeadListView.setLayoutManager(new GridLayoutManager(this, 5));
        groupHeadListView.setAdapter(groupSetItemAdapter);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @OnClick(R.id.btn_random)
    void random() {
        shuffle(groups);
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < groups.length; i++) {
            list.add(groups[i]);
        }

        groupSetItemAdapter.setNewData(list);
    }

    private Random rand = new Random();

    public void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    public void shuffle(int[] arr) {
        int length = arr.length;
        for (int i = length; i > 0; i--) {
            int randInd = rand.nextInt(i);
            swap(arr, randInd, i - 1);
        }
    }

    @OnClick(R.id.btn_config)
    void configSetting() {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putIntArray("group_images", groups);
        intent.putExtras(bundle);
        //设置返回数据
        this.setResult(RESULT_OK, intent);
        finish();
    }

}
