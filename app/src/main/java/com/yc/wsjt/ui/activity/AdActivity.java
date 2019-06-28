package com.yc.wsjt.ui.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.jaeger.library.StatusBarUtil;
import com.just.agentweb.AgentWeb;
import com.yc.wsjt.R;
import com.yc.wsjt.presenter.Presenter;

import butterknife.BindView;

/**
 * Created by myflying on 2018/11/23.
 */


public class AdActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView mBackIv;

    @BindView(R.id.tv_title)
    TextView mTitleTv;

    @BindView(R.id.content_view)
    LinearLayout mContentViewLayout;

    AgentWeb mAgentWeb;

    String adUrl;

    String adTitle = "精选推荐";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ad_view;
    }

    @Override
    protected Presenter getPresenter() {
        return null;
    }

    @Override
    protected void initVars() {
        StatusBarUtil.setLightMode(this);
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.white), 0);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            adUrl = bundle.getString("ad_url");
            adTitle = bundle.getString("ad_title", "精选推荐");
        }
        mTitleTv.setText(adTitle);
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent((LinearLayout) mContentViewLayout, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .createAgentWeb()
                .ready()
                .go(adUrl);
    }

}
