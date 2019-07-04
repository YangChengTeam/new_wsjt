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
import com.yc.wsjt.ui.adapter.WeiXinInfoAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;

/**
 * Created by admin on 2017/4/20.
 */

public class WeiXinModuleFragment extends BaseFragment {

    @BindView(R.id.weixin_list)
    RecyclerView weixinListView;

    WeiXinInfoAdapter weiXinInfoAdapter;

    private DividerGridItemDecoration itemDecoration;

    private List<QuickInfo> quickBarList;

    private List<QuickInfo> wxQuickList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_weixin_module;
    }

    @Override
    public void initVars() {

        DividerGridItemDecoration.Builder builder = new DividerGridItemDecoration.Builder(getContext(), LinearLayoutManager.VERTICAL, 3);
        builder.setShowOtherStyle(true).setDivider(R.drawable.bg_divider_list);
        itemDecoration = builder.build();
        weixinListView.addItemDecoration(itemDecoration);
        weixinListView.setLayoutManager(new GridLayoutManager(getActivity(), 3, RecyclerView.VERTICAL, false));
        quickBarList = ((BaseActivity) getActivity()).mAppDatabase.quickInfoDao().loadQuickInfo();

        if (App.getApp().getModuleInfoWrapper() != null) {
            wxQuickList = App.getApp().getModuleInfoWrapper().getWx();
            if (wxQuickList != null && wxQuickList.size() > 0) {
                for (QuickInfo wxQuickInfo : wxQuickList) {
                    for (QuickInfo barQuick : quickBarList) {
                        if (wxQuickInfo.getId() == barQuick.getId()) {
                            wxQuickInfo.setAddQuickBar(true);
                        }
                    }
                }
            }
        }

        weiXinInfoAdapter = new WeiXinInfoAdapter(getActivity(), wxQuickList, ScreenUtils.getScreenWidth() / 3, true);
        weixinListView.setAdapter(weiXinInfoAdapter);

        weiXinInfoAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.iv_quick) {
                    QuickInfo addQuickInfo = weiXinInfoAdapter.getData().get(position);

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
                    weiXinInfoAdapter.notifyDataSetChanged();
                }
            }
        });
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
        if (event.getModuleInfoWrapper() != null && event.getModuleInfoWrapper().getWx() != null && event.getModuleInfoWrapper().getWx().size() > 0) {
            weiXinInfoAdapter.setNewData(event.getModuleInfoWrapper().getWx());
        }
        if (event.getMessageType() == Constants.REMOVE_QUICK_INFO && event.getAddQuickInfo() != null) {
            for (int i = 0; i < weiXinInfoAdapter.getData().size(); i++) {
                if (event.getAddQuickInfo().getId() == weiXinInfoAdapter.getData().get(i).getId()) {
                    weiXinInfoAdapter.getData().get(i).setAddQuickBar(false);
                    weiXinInfoAdapter.notifyItemChanged(i);
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
