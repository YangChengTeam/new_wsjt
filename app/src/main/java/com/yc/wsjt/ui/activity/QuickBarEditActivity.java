package com.yc.wsjt.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ckr.decoration.DividerGridItemDecoration;
import com.jaeger.library.StatusBarUtil;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.orhanobut.logger.Logger;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.MessageEvent;
import com.yc.wsjt.bean.ModuleInfoRet;
import com.yc.wsjt.bean.ModuleInfoWrapper;
import com.yc.wsjt.bean.QuickInfo;
import com.yc.wsjt.common.Constants;
import com.yc.wsjt.presenter.Presenter;
import com.yc.wsjt.ui.adapter.QuickEditAdapter;
import com.yc.wsjt.ui.fragment.AlipayModuleFragment;
import com.yc.wsjt.ui.fragment.QQModuleFragment;
import com.yc.wsjt.ui.fragment.WeiXinModuleFragment;
import com.yc.wsjt.view.ModuleInfoView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhangdinghui on 2019/4/29.
 */
public class QuickBarEditActivity extends BaseActivity implements ModuleInfoView {

    @BindView(R.id.quick_edit_list)
    RecyclerView editBarListView;

    @BindView(R.id.iv_config)
    TextView mConfigTv;

    @BindView(R.id.tab_layout)
    SmartTabLayout smartTabLayout;

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    QuickEditAdapter quickEditAdapter;

    private boolean quickIsEdit = true;

    private DividerGridItemDecoration itemDecoration;

    public ModuleInfoWrapper moduleInfoWrapper;

    public ModuleInfoWrapper getModuleInfoWrapper() {
        return moduleInfoWrapper;
    }

    public void setModuleInfoWrapper(ModuleInfoWrapper moduleInfoWrapper) {
        this.moduleInfoWrapper = moduleInfoWrapper;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_quick_bar1;
    }

    @Override
    protected Presenter getPresenter() {
        return null;
    }

    @Override
    protected void initVars() {
        StatusBarUtil.setLightMode(this);

        quickEditAdapter = new QuickEditAdapter(this, null, ScreenUtils.getScreenWidth() / 3);

        DividerGridItemDecoration.Builder builder = new DividerGridItemDecoration.Builder(getContext(), LinearLayoutManager.VERTICAL, 3);
        builder.setShowOtherStyle(true).setDivider(R.drawable.bg_divider_list);
        itemDecoration = builder.build();
        //editBarListView.addItemDecoration(itemDecoration);
        editBarListView.setLayoutManager(new GridLayoutManager(this, 3, RecyclerView.VERTICAL, false));

        editBarListView.setAdapter(quickEditAdapter);

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add(R.string.tab_layout_weixin, WeiXinModuleFragment.class)
                .add(R.string.tab_layout_alipay, AlipayModuleFragment.class)
                .add(R.string.tab_layout_qq, QQModuleFragment.class)
                .create());

        viewPager.setAdapter(adapter);
        smartTabLayout.setViewPager(viewPager);
        quickEditAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.iv_quick_delete) {
                    //ToastUtils.showLong("pos--->" + position);
                    mAppDatabase.quickInfoDao().deleteQuickInfo(quickEditAdapter.getData().get(position));
                    quickEditAdapter.getData().remove(position);
                    quickEditAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
        try {
            mAppDatabase.quickInfoDao()
                    .loadQuickInfo()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<List<QuickInfo>>() {
                        @Override
                        public void accept(List<QuickInfo> list) {
                            if (list != null) {
                                quickEditAdapter.setNewData(list);
                            }
                        }
                    });
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initViews() {
        //moduleInfoPresenterImp = new ModuleInfoPresenterImp(this, this);
        //moduleInfoPresenterImp.getModuleList();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        Logger.i("event type--->" + event.getMessageType());
        if (event.getMessageType() == Constants.ADD_QUICK_INFO && event.getAddQuickInfo() != null) {
            event.getAddQuickInfo().setAddDate(TimeUtils.date2Millis(new Date()));
            List<Long> insertIds = mAppDatabase.quickInfoDao().insert(event.getAddQuickInfo());
            Logger.i("ids--->" + insertIds);
            quickEditAdapter.addData(event.getAddQuickInfo());
        }
    }

    @OnClick(R.id.iv_config)
    void config() {
        quickIsEdit = !quickIsEdit;
        mConfigTv.setText(quickIsEdit ? "保存" : "编辑");
        quickEditAdapter.setEdit(quickIsEdit);
        quickEditAdapter.notifyDataSetChanged();
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void dismissProgress() {

    }

    @Override
    public void loadDataSuccess(ModuleInfoRet tData) {
        Logger.i("edit bar list--->" + JSON.toJSONString(tData));
        if (tData != null && tData.getCode() == Constants.SUCCESS) {
            if (tData.getData() != null) {
//                MessageEvent event = new MessageEvent();
//                event.setModuleInfoWrapper(tData.getData());
//                EventBus.getDefault().post(event);
//                setModuleInfoWrapper(tData.getData());
            }
        }
    }

    @Override
    public void loadDataError(Throwable throwable) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
