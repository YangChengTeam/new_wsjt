package com.yc.wsjt.ui.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ckr.decoration.DividerGridItemDecoration;
import com.yc.wsjt.App;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.MessageEvent;
import com.yc.wsjt.bean.QuickInfo;
import com.yc.wsjt.common.Constants;
import com.yc.wsjt.ui.activity.BaseActivity;
import com.yc.wsjt.ui.activity.QuickBarEditActivity;
import com.yc.wsjt.ui.adapter.QQInfoAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;

/**
 * Created by admin on 2017/4/20.
 */

public class QQModuleFragment extends BaseFragment {

    @BindView(R.id.qq_list)
    RecyclerView qqListView;

    QQInfoAdapter qqInfoAdapter;

    private DividerGridItemDecoration itemDecoration;

    private List<QuickInfo> quickBarList;

    private List<QuickInfo> qqQuickList;

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
        qqListView.addItemDecoration(itemDecoration);
        qqListView.setLayoutManager(new GridLayoutManager(getActivity(), 3, RecyclerView.VERTICAL, false));

        quickBarList = ((BaseActivity) getActivity()).mAppDatabase.quickInfoDao().loadQuickInfo();

        if (App.getApp().getModuleInfoWrapper() != null) {
            qqQuickList = App.getApp().getModuleInfoWrapper().getQq();
            if (qqQuickList != null && qqQuickList.size() > 0) {
                for (QuickInfo qqQuickInfo : qqQuickList) {
                    for (QuickInfo barQuick : quickBarList) {
                        if (qqQuickInfo.getId() == barQuick.getId()) {
                            qqQuickInfo.setAddQuickBar(true);
                        }
                    }
                }
            }
        }

        qqInfoAdapter = new QQInfoAdapter(getActivity(), qqQuickList, ScreenUtils.getScreenWidth() / 3, true);
        qqListView.setAdapter(qqInfoAdapter);
        qqInfoAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.iv_quick) {
                    //每次操作之前，查询下最新的记录
                    QuickInfo addQuickInfo = qqInfoAdapter.getData().get(position);

                    if (!addQuickInfo.isAddQuickBar()) {
                        if (((QuickBarEditActivity) getActivity()).getQuickBarTotalCount() == 8) {
                            ToastUtils.showLong("最多只能添加8个");
                            return;
                        }
                    }

                    MessageEvent event = new MessageEvent();
                    event.setMessageType(addQuickInfo.isAddQuickBar() ? Constants.REMOVE_BAR_QUICK : Constants.ADD_QUICK_INFO);
                    event.setAddQuickInfo(addQuickInfo);
                    EventBus.getDefault().post(event);

                    //改变设置后的状态
                    addQuickInfo.setAddQuickBar(!addQuickInfo.isAddQuickBar());
                    qqInfoAdapter.notifyDataSetChanged();
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

        if (event.getMessageType() == Constants.REMOVE_QUICK_INFO && event.getAddQuickInfo() != null) {
            for (int i = 0; i < qqInfoAdapter.getData().size(); i++) {
                if (event.getAddQuickInfo().getId() == qqInfoAdapter.getData().get(i).getId()) {
                    qqInfoAdapter.getData().get(i).setAddQuickBar(false);
                    qqInfoAdapter.notifyItemChanged(i);
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
