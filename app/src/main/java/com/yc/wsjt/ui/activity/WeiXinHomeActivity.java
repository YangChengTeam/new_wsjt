package com.yc.wsjt.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.jaeger.library.StatusBarUtil;
import com.yc.wsjt.R;
import com.yc.wsjt.presenter.Presenter;
import com.yc.wsjt.ui.adapter.WeiXinHomeListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhangdinghui on 2019/6/3.
 */
public class WeiXinHomeActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.chat_list)
    RecyclerView mChatListView;

    @BindView(R.id.iv_add)
    ImageView mAddImageView;

    WeiXinHomeListAdapter weiXinHomeListAdapter;

    BottomSheetDialog bottomSheetDialog;

    LinearLayout mDanLiaoLayout;

    LinearLayout mQunLiaoLayout;

    LinearLayout mPublicxCodeLayout;

    LinearLayout mOtherSetLayout;

    LinearLayout mCancelLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_weixin_home;
    }

    @Override
    protected Presenter getPresenter() {
        return null;
    }

    @Override
    protected void initVars() {
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.colorPrimary), 0);
        StatusBarUtil.setLightMode(this);

        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i + "");
        }
        weiXinHomeListAdapter = new WeiXinHomeListAdapter(this, list);
        mChatListView.setLayoutManager(new LinearLayoutManager(this));
        mChatListView.setAdapter(weiXinHomeListAdapter);

        bottomSheetDialog = new BottomSheetDialog(this);
        View chatInfoTypeView = LayoutInflater.from(this).inflate(R.layout.wx_chat_info_type, null);
        mDanLiaoLayout = chatInfoTypeView.findViewById(R.id.layout_danliao);
        mQunLiaoLayout = chatInfoTypeView.findViewById(R.id.layout_qunliao);
        mPublicxCodeLayout = chatInfoTypeView.findViewById(R.id.layout_public_code);
        mOtherSetLayout = chatInfoTypeView.findViewById(R.id.layout_other_set);
        mCancelLayout = chatInfoTypeView.findViewById(R.id.layout_view_cancel);

        mDanLiaoLayout.setOnClickListener(this);
        mQunLiaoLayout.setOnClickListener(this);
        mPublicxCodeLayout.setOnClickListener(this);
        mOtherSetLayout.setOnClickListener(this);
        mCancelLayout.setOnClickListener(this);

        bottomSheetDialog.setContentView(chatInfoTypeView);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @OnClick(R.id.iv_add)
    void addChatInfo() {
        if (bottomSheetDialog != null && !bottomSheetDialog.isShowing()) {
            bottomSheetDialog.show();
        }
    }

    @Override
    public void onClick(View v) {
        if (bottomSheetDialog != null && bottomSheetDialog.isShowing()) {
            bottomSheetDialog.dismiss();
        }
        switch (v.getId()) {
            case R.id.layout_danliao:
                Intent intent = new Intent(this, WxDanLiaoActivity.class);
                startActivity(intent);
                break;
            case R.id.layout_qunliao:
                Intent intent1 = new Intent(this, WxQunLiaoActivity.class);
                startActivity(intent1);
                break;
            case R.id.layout_public_code:
                Intent intent2 = new Intent(this, WxPublicActivity.class);
                startActivity(intent2);
                break;
            case R.id.layout_other_set:
                Intent intent3 = new Intent(this, WxOtherSetActivity.class);
                startActivity(intent3);
                break;
            case R.id.layout_view_cancel:
                break;
            default:
                break;
        }
    }
}
