package com.yc.wsjt.ui.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.orhanobut.logger.Logger;
import com.yc.wsjt.R;
import com.yc.wsjt.presenter.Presenter;
import com.yc.wsjt.ui.custom.Glide4Engine;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhangdinghui on 2019/6/3.
 */
public class AddGroupUserActivity extends BaseActivity implements View.OnClickListener {
    private static final int REQUEST_CODE_CHOOSE = 1000;

    @BindView(R.id.tv_title)
    TextView mTitleTv;

    @BindView(R.id.iv_add_user_head)
    ImageView mAddUserHeadIv;

    BottomSheetDialog bottomSheetDialog;

    LinearLayout mRandomLayout;

    LinearLayout mWeixinLayout;

    LinearLayout mCustomLayout;

    LinearLayout mCancelLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_group_user;
    }

    @Override
    protected Presenter getPresenter() {
        return null;
    }

    @Override
    protected void initVars() {
        mTitleTv.setText("添加成员");
    }

    @Override
    protected void initViews() {
        bottomSheetDialog = new BottomSheetDialog(this);
        View addGroupUserInfo = LayoutInflater.from(this).inflate(R.layout.add_group_user_view, null);
        mRandomLayout = addGroupUserInfo.findViewById(R.id.layout_random_role);
        mWeixinLayout = addGroupUserInfo.findViewById(R.id.layout_wx_head);
        mCustomLayout = addGroupUserInfo.findViewById(R.id.layout_custom_add);
        mCancelLayout = addGroupUserInfo.findViewById(R.id.layout_view_cancel);

        mRandomLayout.setOnClickListener(this);
        mWeixinLayout.setOnClickListener(this);
        mCustomLayout.setOnClickListener(this);
        mCancelLayout.setOnClickListener(this);
        bottomSheetDialog.setContentView(addGroupUserInfo);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @OnClick(R.id.layout_add_user)
    void addUserInfo() {
        if (bottomSheetDialog != null && !bottomSheetDialog.isShowing()) {
            bottomSheetDialog.show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_random_role:
                break;
            case R.id.layout_wx_head:
                break;
            case R.id.layout_custom_add:
                Matisse.from(this)
                        .choose(MimeType.ofImage())
                        .countable(true)
                        .maxSelectable(1)
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                        .thumbnailScale(0.85f)
                        .imageEngine(new Glide4Engine())
                        .showSingleMediaType(true)
                        .forResult(REQUEST_CODE_CHOOSE);
                break;
            case R.id.layout_view_cancel:
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_CHOOSE:
                    Logger.i(JSONObject.toJSONString(Matisse.obtainPathResult(data)));
                    Glide.with(AddGroupUserActivity.this).load(Matisse.obtainResult(data).get(0)).into(mAddUserHeadIv);
                    break;
            }
        }
    }

    @OnClick(R.id.iv_back)
    void back() {
        finish();
    }
}
