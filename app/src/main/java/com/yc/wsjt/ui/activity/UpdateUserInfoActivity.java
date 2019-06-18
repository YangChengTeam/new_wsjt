package com.yc.wsjt.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jaeger.library.StatusBarUtil;
import com.orhanobut.logger.Logger;
import com.yc.wsjt.App;
import com.yc.wsjt.R;
import com.yc.wsjt.base.IBaseView;
import com.yc.wsjt.bean.ResultInfo;
import com.yc.wsjt.bean.UserInfo;
import com.yc.wsjt.common.Constants;
import com.yc.wsjt.presenter.Presenter;
import com.yc.wsjt.presenter.UpdateInfoPresenterImp;
import com.yc.wsjt.ui.custom.Glide4Engine;
import com.yc.wsjt.ui.custom.GlideCircleTransformWithBorder;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhangdinghui on 2019/6/5.
 */
public class UpdateUserInfoActivity extends BaseActivity implements IBaseView {

    private static final int REQUEST_CODE_CHOOSE = 1000;

    @BindView(R.id.iv_back)
    ImageView mBackIv;

    @BindView(R.id.tv_title)
    TextView mTitleTv;

    @BindView(R.id.iv_user_head)
    ImageView mUserHeadIv;

    @BindView(R.id.tv_nick_name)
    TextView mNickNameTv;

    @BindView(R.id.tv_user_id)
    TextView mUserIdTv;

    private UserInfo mUserInfo;

    UpdateInfoPresenterImp updateInfoPresenterImp;

    private ProgressDialog progressDialog = null;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_update_user_info;
    }

    @Override
    protected Presenter getPresenter() {
        return null;
    }

    @Override
    protected void initVars() {
        StatusBarUtil.setLightMode(this);
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.white), 0);
        mTitleTv.setText("设置");
    }

    @Override
    protected void initViews() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("正在修改");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        updateInfoPresenterImp = new UpdateInfoPresenterImp(this, this);

        if (App.getApp().isLogin) {
            mUserInfo = App.getApp().getmUserInfo();
            if (mUserInfo != null) {
                mNickNameTv.setText(StringUtils.isEmpty(mUserInfo.getNickName()) ? "" : mUserInfo.getNickName());
                mUserIdTv.setText(StringUtils.isEmpty(mUserInfo.getId()) ? "xxxx" : mUserInfo.getId());

                RequestOptions options = new RequestOptions();
                options.transform(new GlideCircleTransformWithBorder(this, 2, ContextCompat.getColor(this, R.color.white)));
                Glide.with(this).load(mUserInfo.getFace()).apply(options).into(mUserHeadIv);
            }
        }
    }

    @OnClick(R.id.iv_user_head)
    void chooseImage() {
        Matisse.from(this)
                .choose(MimeType.ofImage())
                .countable(true)
                .maxSelectable(1)
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(new Glide4Engine())
                .showSingleMediaType(true)
                .forResult(REQUEST_CODE_CHOOSE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_CHOOSE:

                    if (Matisse.obtainPathResult(data) != null && Matisse.obtainPathResult(data).size() > 0) {
                        String outPath = Matisse.obtainPathResult(data).get(0);
                        Logger.i(outPath);
                        if (progressDialog != null && !progressDialog.isShowing()) {
                            progressDialog.show();
                        }
                        updateInfoPresenterImp.updateHead(mUserInfo.getId(), new File(outPath));
                    }

                    if (Matisse.obtainResult(data) != null && Matisse.obtainResult(data).size() > 0) {
                        Glide.with(UpdateUserInfoActivity.this).load(Matisse.obtainResult(data).get(0)).into(mUserHeadIv);
                    }
                    break;
            }
        }
    }

    @OnClick(R.id.layout_nick_name)
    void updateName() {
        Intent intent = new Intent(this, UpdateNameActivity.class);
        startActivity(intent);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void dismissProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void loadDataSuccess(Object tData) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        if (tData != null && tData instanceof ResultInfo) {
            if (((ResultInfo) tData).getCode() == Constants.SUCCESS) {
                ToastUtils.showLong("修改成功");
            }
        }
    }

    @Override
    public void loadDataError(Throwable throwable) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
