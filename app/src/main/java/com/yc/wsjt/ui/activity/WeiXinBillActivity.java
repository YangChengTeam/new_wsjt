package com.yc.wsjt.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.yc.wsjt.R;
import com.yc.wsjt.presenter.Presenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhangdinghui on 2019/5/27.
 */
public class WeiXinBillActivity extends BaseActivity {

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

    private boolean isUse;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bill_set;
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
        mTitleTv.setText("账单");
        mConfigBtn.setVisibility(View.GONE);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            isUse = bundle.getBoolean("is_use", false);
        }
    }

    @OnClick(R.id.btn_add_data)
    void add() {
        Intent intent = new Intent(this, AddBillActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_pre_show)
    void preShow() {
        Intent intent = new Intent(this, WeiXinBillPreActivity.class);
        intent.putExtra("is_use", isUse);
        startActivity(intent);
    }

    @OnClick(R.id.iv_back)
    void back() {
        finish();
    }
}
