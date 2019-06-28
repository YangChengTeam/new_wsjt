package com.yc.wsjt.ui.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.SPUtils;
import com.jaeger.library.StatusBarUtil;
import com.just.agentweb.AgentWeb;
import com.yc.wsjt.R;
import com.yc.wsjt.common.Constants;
import com.yc.wsjt.presenter.Presenter;

import butterknife.BindView;

/**
 * Created by zhangdinghui on 2019/6/5.
 */
public class KeFuHelpActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView mBackIv;

    @BindView(R.id.tv_title)
    TextView mTitleTv;

    @BindView(R.id.content_view)
    LinearLayout mContentViewLayout;

    AgentWeb mAgentWeb;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_kefu_help;
    }

    @Override
    protected Presenter getPresenter() {
        return null;
    }

    @Override
    protected void initVars() {
        StatusBarUtil.setLightMode(this);
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.white), 0);
        mTitleTv.setText("客服帮助");
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        String newHelpUrl = SPUtils.getInstance().getString("kf_help", "");
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent((LinearLayout) mContentViewLayout, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .createAgentWeb()
                .ready()
                .go(Constants.BASE_IMAGE_URL +"/"+ newHelpUrl);
    }
}
