package com.yc.wsjt.ui.activity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.jaeger.library.StatusBarUtil;
import com.orhanobut.logger.Logger;
import com.yc.wsjt.App;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.MessageContent;
import com.yc.wsjt.bean.RedPackageMessage;
import com.yc.wsjt.bean.WeixinQunChatInfo;
import com.yc.wsjt.presenter.Presenter;
import com.yc.wsjt.ui.custom.CustomDateDialog;
import com.yc.wsjt.ui.custom.RoleSelectDialog;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

public class ChatQunSendRedPackageActivity extends BaseActivity implements RoleSelectDialog.ChooseRoleListener, CustomDateDialog.DateSelectListener {

    private static final int REQUEST_CODE_CHOOSE = 1000;

    @BindView(R.id.tv_title)
    TextView mTitleTv;

    @BindView(R.id.btn_config)
    Button mConfigBtn;

    @BindView(R.id.iv_send_user_head)
    ImageView mSendUserHeadIv;

    @BindView(R.id.layout_send_info)
    RelativeLayout mSendInfoLayout;

    @BindView(R.id.tv_send_user_name)
    TextView mSendUserNameTv;

    @BindView(R.id.layout_red_number)
    LinearLayout mRedNumberLayout;

    @BindView(R.id.et_red_count)
    EditText mRedCountEt;

    @BindView(R.id.et_red_number)
    EditText mRedNumberEt;

    @BindView(R.id.et_red_remark)
    EditText mRedRemarkEt;

    @BindView(R.id.tv_send_time)
    TextView mSendTimeTv;

    @BindView(R.id.tv_receive_time)
    TextView mReceiveTimeTv;

    private File outputImage;

    private String sendUserName;

    private String sendUserHead;

    private int CHAT_TYPE = MessageContent.SEND_RED_PACKET;

    CustomDateDialog customDateDialog;

    private int chooseDateType = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_qun_send_red_package;
    }

    @Override
    protected Presenter getPresenter() {
        return null;
    }

    @Override
    protected void initVars() {
        StatusBarUtil.setLightMode(this);
        mTitleTv.setText("群聊发红包");
    }

    @Override
    protected void initViews() {
        customDateDialog = new CustomDateDialog(this, R.style.date_dialog);
        customDateDialog.setDateSelectListener(this);
        mSendTimeTv.setText(TimeUtils.getNowString());
        mReceiveTimeTv.setText(TimeUtils.getNowString());
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

    @OnClick(R.id.btn_config)
    void config() {

        if (StringUtils.isEmpty(sendUserName)) {
            ToastUtils.showLong("请选择发送人");
            return;
        }

        if (StringUtils.isEmpty(mRedCountEt.getText())) {
            ToastUtils.showLong("请输入红包个数");
            return;
        }

        if (StringUtils.isEmpty(mRedNumberEt.getText())) {
            ToastUtils.showLong("请输入金额");
            return;
        }

        //插入一条时间设置记录
        RedPackageMessage redPackageMessage = new RedPackageMessage();
        //redPackageMessage.setWxMainId(App.getApp().getMessageContent().getWxMainId());
        redPackageMessage.setRedCount(Integer.parseInt(mRedCountEt.getText().toString()));
        redPackageMessage.setRedNumber(mRedNumberEt.getText().toString());
        redPackageMessage.setRedDesc(StringUtils.isEmpty(mRedRemarkEt.getText()) ? mRedRemarkEt.getText().toString() : "恭喜发财，大吉大利!");
        redPackageMessage.setMessageType(CHAT_TYPE);
        redPackageMessage.setRedType(1);
        redPackageMessage.setMessageUserName(sendUserName);
        redPackageMessage.setMessageUserHead(sendUserHead);
        Long redId = mAppDatabase.redMessageDao().insert(redPackageMessage);

        //插入到外层的列表中
        WeixinQunChatInfo weixinQunChatInfo = new WeixinQunChatInfo();
        weixinQunChatInfo.setMainId(App.getApp().getMessageContent().getId());
        weixinQunChatInfo.setTypeIcon(R.mipmap.type_hongbao);
        weixinQunChatInfo.setType(CHAT_TYPE);
        weixinQunChatInfo.setChildTabId(redId);
        mAppDatabase.weixinQunChatInfoDao().insert(weixinQunChatInfo);

        finish();
    }

    @Override
    public void chooseRole(int pos, String name, String head, int localHead) {
        if (pos > 0) {
            CHAT_TYPE = MessageContent.RECEIVE_RED_PACKET;
        } else {
            CHAT_TYPE = MessageContent.SEND_RED_PACKET;
        }
        mSendUserNameTv.setText(name);
        sendUserName = name;
        Glide.with(this).load(head).into(mSendUserHeadIv);
        sendUserHead = head;
    }

    @OnClick(R.id.layout_send_time)
    public void sendTime() {
        chooseDateType = 1;

        customDateDialog.show();
        //设置Dialog从窗体底部弹出
        customDateDialog.getWindow().setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams windowParams = customDateDialog.getWindow().getAttributes();
        windowParams.width = ScreenUtils.getScreenWidth();
        windowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        customDateDialog.getWindow().setAttributes(windowParams);
    }

    @OnClick(R.id.layout_receive_time)
    public void receiveTime() {
        chooseDateType = 2;

        customDateDialog.show();
        //设置Dialog从窗体底部弹出
        customDateDialog.getWindow().setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams windowParams = customDateDialog.getWindow().getAttributes();
        windowParams.width = ScreenUtils.getScreenWidth();
        windowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        customDateDialog.getWindow().setAttributes(windowParams);
    }

    @Override
    public void configDate(String selectDate) {
        Logger.i("select date --->" + selectDate);
        if (chooseDateType == 1) {
            mSendTimeTv.setText(selectDate);
        } else {
            mReceiveTimeTv.setText(selectDate);
        }
    }

}
