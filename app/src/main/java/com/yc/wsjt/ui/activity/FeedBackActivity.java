package com.yc.wsjt.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.jaeger.library.StatusBarUtil;
import com.orhanobut.logger.Logger;
import com.yc.wsjt.R;
import com.yc.wsjt.base.IBaseView;
import com.yc.wsjt.bean.QuestionInfo;
import com.yc.wsjt.bean.ResultInfo;
import com.yc.wsjt.common.Constants;
import com.yc.wsjt.presenter.Presenter;
import com.yc.wsjt.presenter.QuestionInfoPresenterImp;
import com.yc.wsjt.ui.custom.Glide4Engine;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhangdinghui on 2019/6/5.
 */
public class FeedBackActivity extends BaseActivity implements IBaseView {

    private static final int REQUEST_CODE_CHOOSE = 1000;

    @BindView(R.id.iv_back)
    ImageView mBackIv;

    @BindView(R.id.tv_title)
    TextView mTitleTv;

    @BindView(R.id.iv_add_feed)
    ImageView mAddFeedIv;

    @BindView(R.id.tv_type1)
    TextView mQuestionType1Tv;

    @BindView(R.id.tv_type2)
    TextView mQuestionType2Tv;

    @BindView(R.id.tv_type3)
    TextView mQuestionType3Tv;

    @BindView(R.id.et_content)
    EditText mContentEt;

    @BindView(R.id.et_contact)
    EditText mContactEt;

    private File outPathFile;

    private int questionType = 1;

    QuestionInfoPresenterImp questionInfoPresenterImp;

    private ProgressDialog progressDialog = null;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_feed_back;
    }

    @Override
    protected Presenter getPresenter() {
        return null;
    }

    @Override
    protected void initVars() {
        StatusBarUtil.setLightMode(this);
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.white), 0);
        mTitleTv.setText("问题反馈");
    }

    @Override
    protected void initViews() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("正在提交");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        questionInfoPresenterImp = new QuestionInfoPresenterImp(this, this);
    }

    @OnClick(R.id.iv_add_feed)
    void addFeedBack() {
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
                    Logger.i(JSONObject.toJSONString(Matisse.obtainPathResult(data)));
                    if (Matisse.obtainPathResult(data) != null && Matisse.obtainPathResult(data).size() > 0) {
                        String outPath = Matisse.obtainPathResult(data).get(0);
                        outPathFile = new File(outPath);

                        Glide.with(FeedBackActivity.this).load(Matisse.obtainPathResult(data).get(0)).into(mAddFeedIv);
                    }
                    break;
            }
        }
    }

    @OnClick(R.id.tv_type1)
    void type1() {
        questionType = 1;
        mQuestionType1Tv.setBackgroundResource(R.drawable.feed_selected);
        mQuestionType1Tv.setTextColor(ContextCompat.getColor(this, R.color.feed_line_select_color));

        mQuestionType2Tv.setBackgroundResource(R.drawable.feed_normal);
        mQuestionType2Tv.setTextColor(ContextCompat.getColor(this, R.color.tab_tv_normal));

        mQuestionType3Tv.setBackgroundResource(R.drawable.feed_normal);
        mQuestionType3Tv.setTextColor(ContextCompat.getColor(this, R.color.tab_tv_normal));
    }

    @OnClick(R.id.tv_type2)
    void type2() {
        questionType = 2;
        mQuestionType2Tv.setBackgroundResource(R.drawable.feed_selected);
        mQuestionType2Tv.setTextColor(ContextCompat.getColor(this, R.color.feed_line_select_color));

        mQuestionType1Tv.setBackgroundResource(R.drawable.feed_normal);
        mQuestionType1Tv.setTextColor(ContextCompat.getColor(this, R.color.tab_tv_normal));

        mQuestionType3Tv.setBackgroundResource(R.drawable.feed_normal);
        mQuestionType3Tv.setTextColor(ContextCompat.getColor(this, R.color.tab_tv_normal));
    }

    @OnClick(R.id.tv_type3)
    void type3() {
        questionType = 3;
        mQuestionType3Tv.setBackgroundResource(R.drawable.feed_selected);
        mQuestionType3Tv.setTextColor(ContextCompat.getColor(this, R.color.feed_line_select_color));

        mQuestionType1Tv.setBackgroundResource(R.drawable.feed_normal);
        mQuestionType1Tv.setTextColor(ContextCompat.getColor(this, R.color.tab_tv_normal));

        mQuestionType2Tv.setBackgroundResource(R.drawable.feed_normal);
        mQuestionType2Tv.setTextColor(ContextCompat.getColor(this, R.color.tab_tv_normal));
    }

    @OnClick(R.id.btn_pre_show)
    public void submit() {

        if (StringUtils.isEmpty(mContentEt.getText())) {
            ToastUtils.showLong("请输入反馈内容");
            return;
        }

        if (StringUtils.isEmpty(mContactEt.getText())) {
            ToastUtils.showLong("请输入联系方式");
            return;
        }

        QuestionInfo questionInfo = new QuestionInfo();
        questionInfo.setType(questionType);
        questionInfo.setContent(mContentEt.getText().toString());
        questionInfo.setContact(mContactEt.getText().toString());

        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.show();
        }

        questionInfoPresenterImp.addQuestion(questionInfo, outPathFile);
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
        Logger.i("feedback--->" + JSON.toJSONString(tData));
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        if (tData != null && tData instanceof ResultInfo) {
            if (((ResultInfo) tData).getCode() == Constants.SUCCESS) {
                ToastUtils.showLong("提交成功");
                finish();
            } else {
                ToastUtils.showLong("提交失败,请重试");
            }
        }
    }

    @Override
    public void loadDataError(Throwable throwable) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        ToastUtils.showLong("提交失败,请重试");
    }
}
