package com.yc.wsjt.ui.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ScreenUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ckr.decoration.DividerGridItemDecoration;
import com.yc.wsjt.App;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.MessageEvent;
import com.yc.wsjt.bean.QuickInfo;
import com.yc.wsjt.common.Constants;
import com.yc.wsjt.ui.activity.QuickBarEditActivity;
import com.yc.wsjt.ui.adapter.QQInfoAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

/**
 * Created by admin on 2017/4/20.
 */

public class QQModuleFragment extends BaseFragment {

    @BindView(R.id.qq_list)
    RecyclerView qqListView;

    QQInfoAdapter qqInfoAdapter;

    private DividerGridItemDecoration itemDecoration;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_qq_module;
    }

    @Override
    public void initVars() {
        DividerGridItemDecoration.Builder builder = new DividerGridItemDecoration.Builder(getContext(), LinearLayoutManager.VERTICAL, 3);
        builder.setShowOtherStyle(true).setDivider(R.drawable.bg_divider_list);
        itemDecoration = builder.build();
        //qqListView.addItemDecoration(itemDecoration);
        qqListView.setLayoutManager(new GridLayoutManager(getActivity(), 3, RecyclerView.VERTICAL, false));

        qqInfoAdapter = new QQInfoAdapter(getActivity(), App.getApp().getModuleInfoWrapper().getQq(), ScreenUtils.getScreenWidth() / 3);
        qqListView.setAdapter(qqInfoAdapter);
        qqInfoAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.iv_add_quick) {
                    QuickInfo addQuickInfo = qqInfoAdapter.getData().get(position);
                    MessageEvent event = new MessageEvent();
                    event.setMessageType(Constants.ADD_QUICK_INFO);
                    event.setAddQuickInfo(addQuickInfo);
                    EventBus.getDefault().post(event);
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (((QuickBarEditActivity) getActivity()).getModuleInfoWrapper() != null) {
            qqInfoAdapter.setNewData(((QuickBarEditActivity) getActivity()).getModuleInfoWrapper().getQq());
        }
    }

    @Override
    public void initViews() {

    }

    @Override
    public void loadData() {

    }

    @Override
    protected void initFragmentConfig() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        if (event.getModuleInfoWrapper() != null && event.getModuleInfoWrapper().getQq() != null && event.getModuleInfoWrapper().getQq().size() > 0) {
            qqInfoAdapter.setNewData(event.getModuleInfoWrapper().getQq());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
