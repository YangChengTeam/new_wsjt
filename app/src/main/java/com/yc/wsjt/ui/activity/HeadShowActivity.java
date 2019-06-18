package com.yc.wsjt.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.HeadInfoRet;
import com.yc.wsjt.common.Constants;
import com.yc.wsjt.presenter.HeadInfoPresenterImp;
import com.yc.wsjt.presenter.Presenter;
import com.yc.wsjt.ui.adapter.AvatarShowAdapter;
import com.yc.wsjt.view.HeadInfoView;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jaeger.library.StatusBarUtil;
import com.orhanobut.logger.Logger;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class HeadShowActivity extends BaseActivity implements HeadInfoView {

    @BindView(R.id.head_list)
    RecyclerView mHeadListView;

    private PagerSnapHelper snapHelper;

    private LinearLayoutManager layoutManager;

    AvatarShowAdapter avatarShowAdapter;

    HeadInfoPresenterImp headInfoPresenterImp;

    private int currentPage = 1;

    private int pageSize = 30;

    private int startPosition;

    private boolean isFirstLoad = true;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_head_show;
    }

    @Override
    protected Presenter getPresenter() {
        return null;
    }

    @Override
    protected void initVars() {

    }

    @Override
    protected void initViews() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.getInt("jump_page") > -1) {
            currentPage = bundle.getInt("jump_page");
        }

        if (bundle != null && bundle.getInt("jump_position") > -1) {
            startPosition = bundle.getInt("jump_position");
        }

        int width = (int) (ScreenUtils.getScreenWidth() * 0.85);
        avatarShowAdapter = new AvatarShowAdapter(this, null, width);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mHeadListView.setLayoutManager(layoutManager);
        mHeadListView.setAdapter(avatarShowAdapter);

        snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(mHeadListView);

        addListener();

        avatarShowAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                currentPage++;
                headInfoPresenterImp.getHeadList(currentPage);
            }
        }, mHeadListView);

        headInfoPresenterImp = new HeadInfoPresenterImp(this, this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        headInfoPresenterImp.getHeadList(currentPage);
    }

    private void addListener() {
        mHeadListView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE://停止滚动
                        Logger.i("current count --->" + layoutManager.findFirstVisibleItemPosition());

                        View view = snapHelper.findSnapView(layoutManager);
                        RecyclerView.ViewHolder viewHolder = recyclerView.getChildViewHolder(view);
                        if (viewHolder != null && viewHolder instanceof BaseViewHolder) {

                        }
                        break;
                    case RecyclerView.SCROLL_STATE_DRAGGING://拖动
                        break;
                    case RecyclerView.SCROLL_STATE_SETTLING://惯性滑动
                        break;
                }
            }
        });
    }

    @Override
    public void setStatusBar() {
        StatusBarUtil.setTranslucentForImageView(this, 0, null);
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

            if (isFirstLoad) {
                if (startPosition < tData.getData().size()) {
                    avatarShowAdapter.setNewData(tData.getData().subList(startPosition, tData.getData().size()));
                } else {
                    avatarShowAdapter.addData(tData.getData());
                }
                isFirstLoad = false;
            } else {
                avatarShowAdapter.addData(tData.getData());
            }

            if (tData.getData().size() == pageSize) {
                avatarShowAdapter.loadMoreComplete();
            } else {
                avatarShowAdapter.loadMoreEnd();
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
