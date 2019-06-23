package com.yc.wsjt.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ScreenUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ckr.decoration.DividerGridItemDecoration;
import com.jaeger.library.StatusBarUtil;
import com.yc.wsjt.App;
import com.yc.wsjt.R;
import com.yc.wsjt.presenter.Presenter;
import com.yc.wsjt.ui.adapter.WeiXinInfoAdapter;

import butterknife.BindView;

public class WeiXinModuleActivity extends BaseActivity {

    @BindView(R.id.weixin_list)
    RecyclerView weixinListView;

    WeiXinInfoAdapter weiXinInfoAdapter;

    private DividerGridItemDecoration itemDecoration;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_weixin_module;
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

        DividerGridItemDecoration.Builder builder = new DividerGridItemDecoration.Builder(getContext(), LinearLayoutManager.VERTICAL, 3);
        builder.setShowOtherStyle(true).setDivider(R.drawable.bg_divider_list);
        itemDecoration = builder.build();
        weixinListView.addItemDecoration(itemDecoration);
        weixinListView.setLayoutManager(new GridLayoutManager(this, 3, RecyclerView.VERTICAL, false));

        weiXinInfoAdapter = new WeiXinInfoAdapter(this, App.getApp().getModuleInfoWrapper() != null ? App.getApp().getModuleInfoWrapper().getWx() : null, ScreenUtils.getScreenWidth() / 3, false);
        weixinListView.setAdapter(weiXinInfoAdapter);

        //Logger.i(JSON.toJSONString(App.getApp().getModuleInfoWrapper().getWx()));

        weiXinInfoAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                int mid = weiXinInfoAdapter.getData().get(position).getId();

                if (mid == 1) {
                    Intent intent = new Intent(WeiXinModuleActivity.this, WeixindanliaoActivity.class);
                    startActivity(intent);
                }
                if (mid == 2) {
                    Intent intent = new Intent(WeiXinModuleActivity.this, WeiXinMoneyActivity.class);
                    startActivity(intent);
                }
                if (mid == 3) {
                    //Intent intent = new Intent(WeiXinModuleActivity.this, RedPackageActivity.class);
                    //startActivity(intent);
                }
                if (mid == 4) {
                    Intent intent = new Intent(WeiXinModuleActivity.this, WeixinqunliaoActivity.class);
                    startActivity(intent);
                    //群聊
                }
                if (mid == 5) {
                    Intent intent = new Intent(WeiXinModuleActivity.this, RedPackageActivity.class);
                    startActivity(intent);
                }
                if (mid == 6) {
                    Intent intent = new Intent(WeiXinModuleActivity.this, TransferActivity.class);
                    startActivity(intent);
                }
                if (mid == 7) {
                    Intent intent = new Intent(WeiXinModuleActivity.this, ExtractSetActivity.class);
                    startActivity(intent);
                }
                if (mid == 8) {
                    Intent intent = new Intent(WeiXinModuleActivity.this, MyWalletActivity.class);
                    startActivity(intent);
                }
                if (mid == 9) {
                    Intent intent = new Intent(WeiXinModuleActivity.this, MoneyDetailListActivity.class);
                    startActivity(intent);
                }
                if (mid == 10) {
                    Intent intent = new Intent(WeiXinModuleActivity.this, NewFriendListActivity.class);
                    startActivity(intent);
                }
                if (mid == 11) {
                    Intent intent = new Intent(WeiXinModuleActivity.this, WeiXinCircleSetActivity.class);
                    startActivity(intent);
                }
                if (mid == 12) {
                    Intent intent = new Intent(WeiXinModuleActivity.this, ChatVideoSetActivity.class);
                    startActivity(intent);
                }
                if (mid == 13) {
                    Intent intent = new Intent(WeiXinModuleActivity.this, ChatVoiceSetActivity.class);
                    startActivity(intent);
                }
                if (mid == 14) {
                    Intent intent = new Intent(WeiXinModuleActivity.this, PaySuccessSetActivity.class);
                    startActivity(intent);
                }
                if (mid == 15) {
                    Intent intent = new Intent(WeiXinModuleActivity.this, WeiXinPayListActivity.class);
                    startActivity(intent);
                }
                if (mid == 16) {
                    Intent intent = new Intent(WeiXinModuleActivity.this, WeiXinBillActivity.class);
                    startActivity(intent);
                }
                if (mid == 17) {
                    Intent intent = new Intent(WeiXinModuleActivity.this, WeiXinHomeActivity.class);
                    startActivity(intent);
                }
                if (mid == 18) {
                    //Intent intent = new Intent(WeiXinModuleActivity.this, ExtractSetActivity.class);
                    //startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }
}
