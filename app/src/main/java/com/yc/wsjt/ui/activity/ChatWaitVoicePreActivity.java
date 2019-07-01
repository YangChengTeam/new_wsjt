package com.yc.wsjt.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.orhanobut.logger.Logger;
import com.yc.wsjt.App;
import com.yc.wsjt.R;
import com.yc.wsjt.ui.custom.OpenVipDialog;
import com.yc.wsjt.ui.custom.VipPayTypeDialog;

/**
 * Created by zhangdinghui on 2019/5/28.
 */
public class ChatWaitVoicePreActivity extends Activity implements OpenVipDialog.VipListener, VipPayTypeDialog.PayListener {

    TextView mChatNameTv;

    ImageView mChatHeadIv;

    private boolean isUse;

    OpenVipDialog openVipDialog;

    VipPayTypeDialog vipPayTypeDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //无title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_chat_wait_voice);
        mChatNameTv = findViewById(R.id.tv_chat_name);
        mChatHeadIv = findViewById(R.id.iv_chat_head);
        if (App.getApp().getTempPerson() != null) {
            mChatNameTv.setText(App.getApp().getTempPerson().mName);
            Glide.with(this).load(App.getApp().getTempPerson().mHead).into(mChatHeadIv);
        }

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
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
