package com.yc.wsjt.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.jaeger.library.StatusBarUtil;
import com.yc.wsjt.R;

/**
 * Created by zhangdinghui on 2019/5/29.
 * 收红包预览界面
 */
public class RedReceivePreActivity extends Activity {

    LinearLayout mTitleLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_red_receive_pre);
        mTitleLayout = findViewById(R.id.layout_title);
        StatusBarUtil.setTranslucentForImageView(this,0, mTitleLayout);
    }
}
