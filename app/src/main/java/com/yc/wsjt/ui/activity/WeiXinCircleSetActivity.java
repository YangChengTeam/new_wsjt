package com.yc.wsjt.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.PhoneUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.jaeger.library.StatusBarUtil;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.CircleBaseSetInfo;
import com.yc.wsjt.presenter.Presenter;
import com.yc.wsjt.ui.adapter.CircleInfoListAdapter;
import com.yc.wsjt.ui.custom.NormalDecoration;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhangdinghui on 2019/5/27.
 */
public class WeiXinCircleSetActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView mBackIv;

    @BindView(R.id.tv_title)
    TextView mTitleTv;

    @BindView(R.id.btn_config)
    Button mConfigBtn;

    @BindView(R.id.btn_add_data)
    Button mAddDataBtn;

    @BindView(R.id.btn_pre_show)
    Button mQueryDataBtn;

    @BindView(R.id.iv_role_head)
    ImageView mRoleHeadIv;

    @BindView(R.id.circle_info_list_view)
    RecyclerView mCircleInfoListView;

    @BindView(R.id.layout_no_data)
    LinearLayout mNoDataLayout;

    CircleInfoListAdapter circleInfoListAdapter;

    private CircleBaseSetInfo circleBaseSetInfo;

    private boolean isUse;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_circle_set;
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
        mTitleTv.setText("微信朋友圈");
        mConfigBtn.setVisibility(View.GONE);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            isUse = bundle.getBoolean("is_use", false);
        }
        circleInfoListAdapter = new CircleInfoListAdapter(this, null);
        mCircleInfoListView.setLayoutManager(new LinearLayoutManager(this));
        mCircleInfoListView.addItemDecoration(new NormalDecoration(ContextCompat.getColor(this, R.color.line_color), 1));
        mCircleInfoListView.setAdapter(circleInfoListAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            if (mAppDatabase.circleBaseSetInfoDao().getItemById(PhoneUtils.getDeviceId()) != null) {
                circleBaseSetInfo = mAppDatabase.circleBaseSetInfoDao().getItemById(PhoneUtils.getDeviceId());
                Glide.with(this).load(circleBaseSetInfo.getRoleHead()).into(mRoleHeadIv);
            }
            if (mAppDatabase.circleInfoDao().getListByDId(PhoneUtils.getDeviceId()) != null) {
                circleInfoListAdapter.setNewData(mAppDatabase.circleInfoDao().getListByDId(PhoneUtils.getDeviceId()));
                mNoDataLayout.setVisibility(View.GONE);
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.layout_chat_setting)
    void circleUserInfo() {
        Intent intent = new Intent(this, CircleBaseSetActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_add_data)
    void add() {
        Intent intent = new Intent(this, AddCircleActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_pre_show)
    void preShow() {
        if (circleBaseSetInfo == null) {
            ToastUtils.showLong("请先设置资料后预览");
            return;
        }
        Intent intent = new Intent(this, WeiXinCircleActivity.class);
        intent.putExtra("is_use", isUse);
        startActivity(intent);
    }

    @OnClick(R.id.iv_back)
    void back() {
        finish();
    }
}
