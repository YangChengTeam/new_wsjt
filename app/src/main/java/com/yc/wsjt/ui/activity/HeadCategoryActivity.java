package com.yc.wsjt.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.HeadInfoRet;
import com.yc.wsjt.common.Constants;
import com.yc.wsjt.presenter.HeadInfoPresenterImp;
import com.yc.wsjt.presenter.Presenter;
import com.yc.wsjt.ui.adapter.HeadInfoAdapter;
import com.yc.wsjt.view.HeadInfoView;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.orhanobut.logger.Logger;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * Created by admin on 2017/4/20.
 */

public class HeadCategoryActivity extends BaseActivity implements HeadInfoView {

    @BindView(R.id.head_list)
    RecyclerView headListView;

    HeadInfoPresenterImp headInfoPresenterImp;

    HeadInfoAdapter headInfoAdapter;

    private int currentPage = 1;

    private int pageSize = 30;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_category;
    }

    @Override
    protected Presenter getPresenter() {
        return null;
    }

    @Override
    protected void initVars() {
        headInfoPresenterImp = new HeadInfoPresenterImp(this, this);
        headInfoAdapter = new HeadInfoAdapter(this, null, (ScreenUtils.getScreenWidth() - SizeUtils.dp2px(8)) / 3);
        headListView.setLayoutManager(new GridLayoutManager(this, 3));
        headListView.setAdapter(headInfoAdapter);

        View topEmptyView = new View(this);
        topEmptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, SizeUtils.dp2px(4)));
        headInfoAdapter.setHeaderView(topEmptyView);

        headInfoAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                currentPage++;
                headInfoPresenterImp.getHeadList(currentPage);
            }
        }, headListView);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        headInfoPresenterImp.getHeadList(currentPage);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void dismissProgress() {

    }

    @Override
    public void loadDataSuccess(HeadInfoRet tData) {
        if (tData != null && tData.getCode() == Constants.SUCCESS) {
            Logger.i(JSON.toJSONString(tData));

            if (currentPage == 1) {
                headInfoAdapter.setNewData(tData.getData());
            } else {
                headInfoAdapter.addData(tData.getData());
            }

            if (tData.getData().size() == pageSize) {
                headInfoAdapter.loadMoreComplete();
            } else {
                headInfoAdapter.loadMoreEnd();
            }
        } else {
            if (currentPage == 1) {
                ToastUtils.showLong("无数据");
            } else {
                ToastUtils.showLong("所有数据加载完成");
            }
        }
    }

    @Override
    public void loadDataError(Throwable throwable) {

    }
}
