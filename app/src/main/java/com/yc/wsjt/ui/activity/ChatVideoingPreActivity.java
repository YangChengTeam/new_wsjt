package com.yc.wsjt.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.orhanobut.logger.Logger;
import com.yc.wsjt.R;
import com.yc.wsjt.ui.custom.OpenVipDialog;
import com.yc.wsjt.ui.custom.VipPayTypeDialog;

/**
 * Created by zhangdinghui on 2019/5/28.
 */
public class ChatVideoingPreActivity extends Activity implements OpenVipDialog.VipListener, VipPayTypeDialog.PayListener {

    private boolean isUse;

    OpenVipDialog openVipDialog;

    VipPayTypeDialog vipPayTypeDialog;

    private String myVideoBg;

    private String otherSideVideoBg;

    private String videoTime;

    ImageView mMyVideoBgIv;

    ImageView mOtherSideVideoBgIv;

    TextView mVideoTimeTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //无title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_chat_video_ing_show);

        mMyVideoBgIv = findViewById(R.id.iv_my_video_bg);
        mOtherSideVideoBgIv = findViewById(R.id.iv_other_side_video_bg);
        mVideoTimeTv = findViewById(R.id.tv_video_time);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            isUse = bundle.getBoolean("is_use", false);
            myVideoBg = bundle.getString("my_video_bg");
            otherSideVideoBg = bundle.getString("other_side_bg");
            videoTime = bundle.getString("video_time");
        }

        Glide.with(this).load(myVideoBg).into(mMyVideoBgIv);
        Glide.with(this).load(otherSideVideoBg).into(mOtherSideVideoBgIv);
        mVideoTimeTv.setText(videoTime);

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
