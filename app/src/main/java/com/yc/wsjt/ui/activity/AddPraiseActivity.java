package com.yc.wsjt.ui.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jaeger.library.StatusBarUtil;
import com.yc.wsjt.App;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.MessageContent;
import com.yc.wsjt.bean.SystemTipsMessage;
import com.yc.wsjt.bean.WeixinChatInfo;
import com.yc.wsjt.bean.WeixinQunChatInfo;
import com.yc.wsjt.presenter.Presenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhangdinghui on 2019/5/8.
 */
public class AddPraiseActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView mTitleTv;

    @BindView(R.id.btn_config)
    Button mConfigBtn;

    @BindView(R.id.et_praise)
    EditText mPraiseEt;

    @BindView(R.id.btn_random)
    Button mRandomBtn;

    private int circleId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_praise;
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

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            circleId = bundle.getInt("circle_id");
        }
        mTitleTv.setText("添加点赞标题");
    }

    @OnClick(R.id.btn_random)
    void randomPraise() {

    }

    @OnClick(R.id.btn_config)
    void config() {

        if (StringUtils.isEmpty(mPraiseEt.getText())) {
            ToastUtils.showLong("请输入点赞内容");
            return;
        }

        mAppDatabase.circleInfoDao().updatePraiseInfo(mPraiseEt.getText().toString(), circleId);

        finish();
    }

    @OnClick(R.id.iv_back)
    void back() {
        finish();
    }
}
