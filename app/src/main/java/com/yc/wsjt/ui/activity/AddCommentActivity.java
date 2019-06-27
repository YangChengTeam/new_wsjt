package com.yc.wsjt.ui.activity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jaeger.library.StatusBarUtil;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.CommentInfo;
import com.yc.wsjt.presenter.Presenter;
import com.yc.wsjt.ui.custom.InputDialog;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhangdinghui on 2019/5/8.
 */
public class AddCommentActivity extends BaseActivity implements InputDialog.InputTxtListener {

    @BindView(R.id.tv_title)
    TextView mTitleTv;

    @BindView(R.id.btn_config)
    Button mConfigBtn;

    @BindView(R.id.et_comment_content)
    EditText mCommentContentEt;

    @BindView(R.id.tv_comment_user_name)
    TextView mCommentUserNameTv;

    @BindView(R.id.tv_reply_user_name)
    TextView mReplyUserNameTv;

    private int circleId;

    InputDialog inputDialog;

    private int inputType = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_comment;
    }

    @Override
    protected Presenter getPresenter() {
        return null;
    }

    @Override
    protected void initVars() {
        StatusBarUtil.setLightMode(this);
    }

    @Override
    protected void initViews() {
        inputDialog = new InputDialog(this, R.style.scale_dialog, "设置评论人");
        inputDialog.setTxtListener(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            circleId = bundle.getInt("circle_id");
        }
        mTitleTv.setText("添加评论");
    }

    @OnClick(R.id.layout_comment_user)
    void commentUser() {
        inputType = 1;
        inputDialog.show();
        inputDialog.setmTitle("设置评论人");
        WindowManager.LayoutParams windowParams = inputDialog.getWindow().getAttributes();
        windowParams.width = (int) (ScreenUtils.getScreenWidth() * 0.75);
        windowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        inputDialog.getWindow().setAttributes(windowParams);
    }

    @OnClick(R.id.layout_reply_user)
    void replyUser() {
        inputType = 2;
        inputDialog.show();
        inputDialog.setmTitle("设置评论对象");

        WindowManager.LayoutParams windowParams = inputDialog.getWindow().getAttributes();
        windowParams.width = (int) (ScreenUtils.getScreenWidth() * 0.75);
        windowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        inputDialog.getWindow().setAttributes(windowParams);
    }

    @OnClick(R.id.btn_config)
    void config() {

        if (StringUtils.isEmpty(mCommentUserNameTv.getText())) {
            ToastUtils.showLong("请输入评论人");
            return;
        }

        if (StringUtils.isEmpty(mReplyUserNameTv.getText())) {
            ToastUtils.showLong("请输入评论对象");
            return;
        }

        if (StringUtils.isEmpty(mCommentContentEt.getText())) {
            ToastUtils.showLong("请输入评论内容");
            return;
        }
        CommentInfo commentInfo = new CommentInfo();
        commentInfo.setCircleId(circleId);
        commentInfo.setSendUserName(mCommentUserNameTv.getText().toString());
        commentInfo.setReplyUserName(mReplyUserNameTv.getText().toString());
        commentInfo.setReplyContent(mCommentContentEt.getText().toString());
        mAppDatabase.commentInfoDao().insert(commentInfo);

        finish();
    }

    @OnClick(R.id.iv_back)
    void back() {
        finish();
    }

    @Override
    public void inputTxt(String inputTxt) {
        if (inputType == 1) {
            mCommentUserNameTv.setText(inputTxt);
        } else {
            mReplyUserNameTv.setText(inputTxt);
        }
    }
}
