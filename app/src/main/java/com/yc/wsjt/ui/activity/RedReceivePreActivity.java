package com.yc.wsjt.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jaeger.library.StatusBarUtil;
import com.yc.wsjt.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhangdinghui on 2019/5/29.
 * 收红包预览界面
 */
public class RedReceivePreActivity extends Activity {

    @BindView(R.id.layout_title)
    LinearLayout mTitleLayout;

    @BindView(R.id.iv_send_user_head)
    ImageView mSendUserHeadIv;

    @BindView(R.id.tv_send_user_name)
    TextView mSendUserNameTv;

    @BindView(R.id.tv_red_remark)
    TextView mRedRemarkTv;

    @BindView(R.id.tv_red_money)
    TextView mRedMoneyTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_red_receive_pre);
        ButterKnife.bind(this);
        StatusBarUtil.setTranslucentForImageView(this, 0, mTitleLayout);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mSendUserNameTv.setText(bundle.getString("send_user_name", ""));
            mRedRemarkTv.setText(bundle.getString("red_remark", "恭喜发财，大吉大利"));
            mRedMoneyTv.setText("¥" + bundle.getString("red_money"));

            RequestOptions requestOptions = new RequestOptions();
            requestOptions.error(R.mipmap.user_head_def);
            Glide.with(this).load(bundle.getString("send_user_head")).apply(requestOptions).into(mSendUserHeadIv);
        }
    }
}
