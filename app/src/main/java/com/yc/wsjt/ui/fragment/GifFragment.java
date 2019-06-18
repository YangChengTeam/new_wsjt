package com.yc.wsjt.ui.fragment;

import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.GifListRet;
import com.yc.wsjt.common.Constants;
import com.yc.wsjt.presenter.GifListPresenterImp;
import com.yc.wsjt.ui.adapter.GifListAdapter;
import com.yc.wsjt.view.GifListView;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jaeger.library.StatusBarUtil;
import com.orhanobut.logger.Logger;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * Created by admin on 2017/4/20.
 */

public class GifFragment extends BaseFragment implements GifListView {
    @BindView(R.id.gif_list)
    RecyclerView gifListView;

    GifListPresenterImp gifListPresenterImp;

    GifListAdapter gifListAdapter;

    private int currentPage = 1;

    private int pageSize = 30;

    @Override
    protected int getContentView() {
        return R.layout.fragment_gif;
    }

    @Override
    public void initVars() {
        gifListPresenterImp = new GifListPresenterImp(this, getActivity());
        gifListAdapter = new GifListAdapter(getActivity(), null, (ScreenUtils.getScreenWidth() - SizeUtils.dp2px(8)) / 3);
        gifListView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        gifListView.setAdapter(gifListAdapter);

        View topEmptyView = new View(getActivity());
        topEmptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, SizeUtils.dp2px(4)));
        gifListAdapter.setHeaderView(topEmptyView);

        gifListAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                currentPage++;
                gifListPresenterImp.getGifList(currentPage);
            }
        }, gifListView);
    }

    @Override
    public void initViews() {
        //setTopViewBgColor();
    }

    public void setTopViewBgColor(){
        StatusBarUtil.setColor(getActivity(), ContextCompat.getColor(getActivity(), R.color.color_yellow), 0);
    }

    @Override
    public void loadData() {
        //gifListPresenterImp.getGifList(currentPage);
    }

    @Override
    protected void initFragmentConfig() {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void dismissProgress() {

    }

    @Override
    public void loadDataSuccess(GifListRet tData) {
        if (tData != null && tData.getCode() == Constants.SUCCESS) {
            Logger.i(JSON.toJSONString(tData));

            if (currentPage == 1) {
                gifListAdapter.setNewData(tData.getData());
            } else {
                gifListAdapter.addData(tData.getData());
            }

            if (tData.getData().size() == pageSize) {
                gifListAdapter.loadMoreComplete();
            } else {
                gifListAdapter.loadMoreEnd();
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
