package com.yc.wsjt.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jaeger.library.StatusBarUtil;
import com.yc.wsjt.App;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.UserInfo;
import com.yc.wsjt.bean.VipInfoRet;
import com.yc.wsjt.common.Constants;
import com.yc.wsjt.presenter.Presenter;
import com.yc.wsjt.presenter.VipInfoPresenterImp;
import com.yc.wsjt.ui.adapter.VipRenewListAdapter;
import com.yc.wsjt.view.VipInfoView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhangdinghui on 2019/6/5.
 */
public class VipInfoActivity extends BaseActivity implements VipInfoView {

    @BindView(R.id.iv_back)
    ImageView mBackIv;

    @BindView(R.id.tv_title)
    TextView mTitleTv;

    @BindView(R.id.tv_user_id)
    TextView mUserIdTv;

    @BindView(R.id.tv_over_date)
    TextView mOverDateTv;

    @BindView(R.id.vip_list)
    RecyclerView mVipListView;

    VipRenewListAdapter vipRenewListAdapter;

    private UserInfo mUserInfo;

    private VipInfoPresenterImp vipInfoPresenterImp;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_vip_info;
    }

    @Override
    protected Presenter getPresenter() {
        return null;
    }

    @Override
    protected void initVars() {
        StatusBarUtil.setLightMode(this);
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.white), 0);
        mTitleTv.setText("我的VIP");
    }

    @Override
    protected void initViews() {
        vipInfoPresenterImp = new VipInfoPresenterImp(this, null);
        vipInfoPresenterImp.getVipList();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        if (App.getApp().isLogin) {
            mUserInfo = App.getApp().getmUserInfo();
            if (mUserInfo != null) {
                mUserIdTv.setText(mUserInfo.getId());
                if (mUserInfo.getExpiresTime() > 0) {
                    mOverDateTv.setText(TimeUtils.millis2String(mUserInfo.getExpiresTime() * 1000));
                }
            }
        }

        vipRenewListAdapter = new VipRenewListAdapter(this, null);
        mVipListView.setLayoutManager(new LinearLayoutManager(this));
        mVipListView.setAdapter(vipRenewListAdapter);
        vipRenewListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.btn_renew) {
                    ToastUtils.showLong("续费" + position);
                }
            }
        });
    }

    @OnClick(R.id.layout_declare)
    void declare() {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void dismissProgress() {

    }

    @Override
    public void loadDataSuccess(VipInfoRet tData) {
        if (tData != null && tData.getCode() == Constants.SUCCESS) {
            if (tData.getData() != null && tData.getData().size() > 0) {
                vipRenewListAdapter.setNewData(tData.getData());
            }
        }
    }

    @Override
    public void loadDataError(Throwable throwable) {

    }
}
