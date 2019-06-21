package com.yc.wsjt.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yc.wsjt.App;
import com.yc.wsjt.R;

/**
 * Created by zhangdinghui on 2019/5/28.
 */
public class ChatWaitVoicePreActivity extends Activity {

    TextView mChatNameTv;

    ImageView mChatHeadIv;

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
    }

}
