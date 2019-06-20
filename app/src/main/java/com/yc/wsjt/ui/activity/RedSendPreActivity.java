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
 * 发红包预览界面
 */
public class RedSendPreActivity extends Activity {

    @BindView(R.id.layout_title)
    LinearLayout mTitleLayout;

    @BindView(R.id.iv_send_user_head)
    ImageView mSendUserHeadIv;

    @BindView(R.id.tv_send_user_name)
    TextView mSendUserNameTv;

    @BindView(R.id.tv_red_remark)
    TextView mRedRemarkTv;

    @BindView(R.id.tv_total_money)
    TextView mTotalMoneyTv;

    @BindView(R.id.iv_receive_user_head)
    ImageView mReceiveUserHeadIv;

    @BindView(R.id.tv_receive_user_name)
    TextView mReceiveUserNameTv;

    @BindView(R.id.tv_receive_date)
    TextView mReceiveDateTv;

    @BindView(R.id.tv_receive_money)
    TextView mReceiveMoneyTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_red_send_pre);
        ButterKnife.bind(this);
        StatusBarUtil.setTranslucentForImageView(this, 0, mTitleLayout);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mSendUserNameTv.setText(bundle.getString("send_user_name", ""));
            mRedRemarkTv.setText(bundle.getString("red_remark", "恭喜发财，大吉大利"));

            RequestOptions requestOptions = new RequestOptions();
            requestOptions.error(R.mipmap.user_head_def);
            Glide.with(this).load(bundle.getString("send_user_head")).apply(requestOptions).into(mSendUserHeadIv);

            mTotalMoneyTv.setText("1个红包共" + bundle.getString("red_money") + "元");
            //receive
            mReceiveUserNameTv.setText(bundle.getString("receive_user_name"));
            mReceiveMoneyTv.setText(bundle.getString("red_money", "0.00") + "元");
            mReceiveDateTv.setText(bundle.getString("red_receive_date"));
            Glide.with(this).load(bundle.getString("receive_user_head")).apply(requestOptions).into(mReceiveUserHeadIv);
        }
    }
}
