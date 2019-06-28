package com.yc.wsjt.ui.activity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.jaeger.library.StatusBarUtil;
import com.yc.wsjt.App;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.MessageContent;
import com.yc.wsjt.bean.RedPackageMessage;
import com.yc.wsjt.bean.WeixinQunChatInfo;
import com.yc.wsjt.presenter.Presenter;
import com.yc.wsjt.ui.custom.RoleSelectDialog;

import butterknife.BindView;
import butterknife.OnClick;

public class ChatQunReceiveRedPackageActivity extends BaseActivity implements RoleSelectDialog.ChooseRoleListener {

    @BindView(R.id.tv_title)
    TextView mTitleTv;

    @BindView(R.id.btn_config)
    Button mConfigBtn;

    @BindView(R.id.tv_other_side_name)
    TextView mOtherSideNameTv;

    @BindView(R.id.iv_other_side_head)
    ImageView mOtherSideHeadIv;

    @BindView(R.id.tv_from_me_red)
    TextView mFromMeRedTv;

    @BindView(R.id.tv_get_other_side_red)
    TextView mGetOtherSideTv;

    private int CHAT_TYPE = MessageContent.QUN_RECEIVE_RED;

    private String sendUserName;

    private String sendUserHead;

    private int receiveType = 1;

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
        StatusBarUtil.setLightMode(this);
        mTitleTv.setText("群聊发红包");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    public void chooseRole(int pos, String name, String head, int localHead) {
        mOtherSideNameTv.setText(name);
        sendUserName = name;
        Glide.with(this).load(head).into(mOtherSideHeadIv);
        sendUserHead = head;
    }

    @OnClick(R.id.layout_other_side_info)
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

    @OnClick(R.id.tv_from_me_red)
    void fromMeRed() {
        receiveType = 1;
        mFromMeRedTv.setBackgroundResource(R.drawable.choose_type_selected);
        mFromMeRedTv.setTextColor(ContextCompat.getColor(this, R.color.white));

        mGetOtherSideTv.setBackgroundResource(R.drawable.choose_type_normal);
        mGetOtherSideTv.setTextColor(ContextCompat.getColor(this, R.color.black));
    }

    @OnClick(R.id.tv_get_other_side_red)
    void getOtherSideRed() {
        receiveType = 2;
        mFromMeRedTv.setBackgroundResource(R.drawable.choose_type_normal);
        mFromMeRedTv.setTextColor(ContextCompat.getColor(this, R.color.black));

        mGetOtherSideTv.setBackgroundResource(R.drawable.choose_type_selected);
        mGetOtherSideTv.setTextColor(ContextCompat.getColor(this, R.color.white));
    }

    @OnClick(R.id.btn_config)
    void config() {
        if (StringUtils.isEmpty(sendUserName)) {
            ToastUtils.showLong("请选择对方人");
            return;
        }

        //插入一条时间设置记录
        RedPackageMessage redPackageMessage = new RedPackageMessage();
        //redPackageMessage.setWxMainId(App.getApp().getMessageContent().getWxMainId());
        redPackageMessage.setMessageType(CHAT_TYPE);
        redPackageMessage.setRedType(2);
        redPackageMessage.setReceiveType(receiveType);
        redPackageMessage.setOtherSideName(sendUserName);
        redPackageMessage.setOtherSideHead(sendUserHead);

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
}
