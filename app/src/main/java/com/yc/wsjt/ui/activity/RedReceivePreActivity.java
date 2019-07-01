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
import com.orhanobut.logger.Logger;
import com.yc.wsjt.R;
import com.yc.wsjt.ui.custom.OpenVipDialog;
import com.yc.wsjt.ui.custom.VipPayTypeDialog;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhangdinghui on 2019/5/29.
 * 收红包预览界面
 */
public class RedReceivePreActivity extends Activity implements OpenVipDialog.VipListener, VipPayTypeDialog.PayListener {

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

    private boolean isUse;

    OpenVipDialog openVipDialog;

    VipPayTypeDialog vipPayTypeDialog;

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
            isUse = bundle.getBoolean("is_use", false);
        }

        openVipDialog = new OpenVipDialog(this, R.style.custom_dialog);
        openVipDialog.setVipListener(this);
        vipPayTypeDialog = new VipPayTypeDialog(this, R.style.custom_dialog);
        vipPayTypeDialog.setPayListener(this);
        if (!isUse) {
            if (openVipDialog != null && !openVipDialog.isShowing()) {
                openVipDialog.show();
                return;
            }
        }
    }

    @Override
    public void addComment() {
    }

    @Override
    public void openVip() {
        if (vipPayTypeDialog != null && !vipPayTypeDialog.isShowing()) {
            vipPayTypeDialog.show();
        }
    }

    @Override
    public void closeOpenVip() {
        finish();
    }

    @Override
    public void pay() {
        Logger.i("打开支付--->");
    }

    @Override
    public void payClose() {
        Logger.i("支付界面关闭--->");
        finish();
    }
}
