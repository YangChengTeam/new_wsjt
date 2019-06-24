package com.yc.wsjt.ui.activity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.jaeger.library.StatusBarUtil;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.MessageContent;
import com.yc.wsjt.presenter.Presenter;
import com.yc.wsjt.ui.custom.RoleSelectDialog;

import butterknife.BindView;
import butterknife.OnClick;

public class ChatQunReceiveRedPackageActivity extends BaseActivity implements RoleSelectDialog.ChooseRoleListener {

    private String sendUserName;

    private String sendUserHead;

    private int CHAT_TYPE = MessageContent.RECEIVE_RED_PACKET;

    @BindView(R.id.tv_other_side_name)
    TextView mOtherSideNameTv;

    @BindView(R.id.iv_other_side_head)
    ImageView mOtherSideHeadIv;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_qun_receive_red_package;
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

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @OnClick(R.id.layout_send_info)
    void changeSendRole() {
        RoleSelectDialog roleSelectDialog = new RoleSelectDialog(this, R.style.custom_dialog);
        roleSelectDialog.setChooseRoleListener(this);
        roleSelectDialog.show();

        roleSelectDialog.setCanceledOnTouchOutside(true);
        WindowManager.LayoutParams windowParams = roleSelectDialog.getWindow().getAttributes();
        windowParams.width = (int) (ScreenUtils.getScreenWidth() * 0.92);
        windowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        roleSelectDialog.getWindow().setAttributes(windowParams);
    }

    @Override
    public void chooseRole(int pos, String name, String head, int localHead) {
        if (pos > 0) {
            CHAT_TYPE = MessageContent.RECEIVE_RED_PACKET;
        } else {
            CHAT_TYPE = MessageContent.SEND_RED_PACKET;
        }
        mOtherSideNameTv.setText(name);
        sendUserName = name;
        Glide.with(this).load(head).into(mOtherSideHeadIv);
        sendUserHead = head;
    }

    @OnClick(R.id.btn_config)
    void config() {
        if (StringUtils.isEmpty(sendUserName)) {
            ToastUtils.showLong("请选择对方人");
            return;
        }
    }
}
