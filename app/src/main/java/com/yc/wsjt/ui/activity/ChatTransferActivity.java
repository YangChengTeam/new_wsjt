package com.yc.wsjt.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.PathUtils;
import com.blankj.utilcode.util.SPUtils;
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
import com.yc.wsjt.bean.TransferMessage;
import com.yc.wsjt.bean.WeixinChatInfo;
import com.yc.wsjt.common.Constants;
import com.yc.wsjt.presenter.Presenter;
import com.yc.wsjt.ui.custom.CustomDateDialog;
import com.zhihu.matisse.Matisse;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 转账-类型
 */
public class ChatTransferActivity extends BaseActivity implements CustomDateDialog.DateSelectListener {

    private static final int REQUEST_CODE_CHOOSE = 1000;

    @BindView(R.id.btn_config)
    Button mConfigBtn;

    @BindView(R.id.layout_send_info)
    RelativeLayout mSendInfoLayout;

    @BindView(R.id.tv_send_user_name)
    TextView mSendUserNameTv;

    @BindView(R.id.iv_send_user_head)
    ImageView mSendUserHeadIv;

    @BindView(R.id.layout_send_time)
    RelativeLayout mSendTimeLayout;

    @BindView(R.id.layout_receive_time)
    RelativeLayout mReceiveTimeLayout;

    @BindView(R.id.tv_send_transfer)
    TextView mSendTransferTv;

    @BindView(R.id.tv_receive_transfer)
    TextView mReceiveTransferTv;

    @BindView(R.id.layout_transfer_remark)
    LinearLayout mTransferRemarkLayout;

    @BindView(R.id.tv_send_transfer_time)
    TextView mSendTransferTimeTv;

    @BindView(R.id.tv_receive_transfer_time)
    TextView mReceiveTransferTimeTv;

    @BindView(R.id.et_transfer_number)
    EditText mTransferNumberEt;

    @BindView(R.id.et_transfer_remark)
    EditText mTransferRemarkEt;

    private File outputImage;

    private int chooseType = 1;

    private int transType = 1; //转账(1)，收款

    CustomDateDialog customDateDialog;

    boolean isMySelf = true;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_chat_transfer;
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
        customDateDialog = new CustomDateDialog(this, R.style.date_dialog);
        customDateDialog.setDateSelectListener(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        if (App.getApp().chatDataInfo != null) {
            isMySelf = SPUtils.getInstance().getBoolean(Constants.IS_SELF, true);
            mSendUserNameTv.setText(isMySelf ? App.getApp().chatDataInfo.getPersonName() : App.getApp().chatDataInfo.getOtherPersonName());

            if (isMySelf) {
                if (StringUtils.isEmpty(App.getApp().chatDataInfo.getPersonHead())) {
                    Glide.with(this).load(R.mipmap.user_head_def).into(mSendUserHeadIv);
                } else {
                    Glide.with(this).load(App.getApp().chatDataInfo.getPersonHead()).into(mSendUserHeadIv);
                }
            } else {
                if (StringUtils.isEmpty(App.getApp().chatDataInfo.getOtherPersonHead())) {
                    Glide.with(this).load(R.mipmap.user_head_def).into(mSendUserHeadIv);
                } else {
                    Glide.with(this).load(App.getApp().chatDataInfo.getOtherPersonHead()).into(mSendUserHeadIv);
                }
            }
        }
    }

    @OnClick(R.id.layout_send_time)
    void sendTime() {
        chooseType = 1;
        customDateDialog.show();

        //设置Dialog从窗体底部弹出
        customDateDialog.getWindow().setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams windowParams = customDateDialog.getWindow().getAttributes();
        windowParams.width = ScreenUtils.getScreenWidth();
        windowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        customDateDialog.getWindow().setAttributes(windowParams);
    }

    @OnClick(R.id.layout_receive_time)
    void receiveTime() {
        chooseType = 2;
        customDateDialog.show();

        //设置Dialog从窗体底部弹出
        customDateDialog.getWindow().setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams windowParams = customDateDialog.getWindow().getAttributes();
        windowParams.width = ScreenUtils.getScreenWidth();
        windowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        customDateDialog.getWindow().setAttributes(windowParams);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_CHOOSE:
                    Logger.i(JSONObject.toJSONString(Matisse.obtainPathResult(data)));
                    if (Matisse.obtainResult(data) != null && Matisse.obtainResult(data).size() > 0) {
                        outputImage = new File(PathUtils.getExternalAppPicturesPath(), TimeUtils.getNowMills() + ".png");
                        Logger.i("out path--->" + outputImage.getAbsolutePath());
                    }
                    break;
            }
        }
    }

    @OnClick(R.id.layout_send_info)
    void changeSendRole() {
        if (App.getApp().chatDataInfo != null) {
            isMySelf = SPUtils.getInstance().getBoolean(Constants.IS_SELF, true);
            isMySelf = !isMySelf;
            SPUtils.getInstance().put(Constants.IS_SELF, isMySelf);
            mSendUserNameTv.setText(isMySelf ? App.getApp().chatDataInfo.getPersonName() : App.getApp().chatDataInfo.getOtherPersonName());
            if (isMySelf) {
                if (StringUtils.isEmpty(App.getApp().chatDataInfo.getPersonHead())) {
                    Glide.with(this).load(R.mipmap.user_head_def).into(mSendUserHeadIv);
                } else {
                    Glide.with(this).load(App.getApp().chatDataInfo.getPersonHead()).into(mSendUserHeadIv);
                }
            } else {
                if (StringUtils.isEmpty(App.getApp().chatDataInfo.getOtherPersonHead())) {
                    Glide.with(this).load(R.mipmap.user_head_def).into(mSendUserHeadIv);
                } else {
                    Glide.with(this).load(App.getApp().chatDataInfo.getOtherPersonHead()).into(mSendUserHeadIv);
                }
            }
        }
    }

    @OnClick(R.id.tv_send_transfer)
    void sendTransfer() {
        transType = 1;
        mSendTransferTv.setBackgroundResource(R.drawable.choose_type_selected);
        mSendTransferTv.setTextColor(ContextCompat.getColor(this, R.color.white));

        mReceiveTransferTv.setBackgroundResource(R.drawable.choose_type_normal);
        mReceiveTransferTv.setTextColor(ContextCompat.getColor(this, R.color.black));
        mTransferRemarkLayout.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.tv_receive_transfer)
    void receiveTransfer() {
        transType = 2;
        mSendTransferTv.setBackgroundResource(R.drawable.choose_type_normal);
        mSendTransferTv.setTextColor(ContextCompat.getColor(this, R.color.black));

        mReceiveTransferTv.setBackgroundResource(R.drawable.choose_type_selected);
        mReceiveTransferTv.setTextColor(ContextCompat.getColor(this, R.color.white));
        mTransferRemarkLayout.setVisibility(View.GONE);
    }

    @OnClick(R.id.btn_config)
    void config() {
        if (StringUtils.isEmpty(mTransferNumberEt.getText())) {
            ToastUtils.showLong("请输入金额");
            return;
        }

        int type = MessageContent.SEND_TRANSFER;
        if (!SPUtils.getInstance().getBoolean(Constants.IS_SELF, true)) {
            type = MessageContent.RECEIVE_TRANSFER;
        }

        //插入一条时间设置记录
        TransferMessage transferMessage = new TransferMessage();
        transferMessage.setMessageType(type);
        transferMessage.setTransferType(transType);
        transferMessage.setTransferNum(mTransferNumberEt.getText().toString());
        transferMessage.setTransferDesc(mTransferRemarkEt.getText().toString());
        transferMessage.setSendTime(mSendTransferTimeTv.getText().toString());
        transferMessage.setReceiveTime(mReceiveTransferTimeTv.getText().toString());
        transferMessage.setMessageUserName(isMySelf ? App.getApp().chatDataInfo.getPersonName() : App.getApp().chatDataInfo.getOtherPersonName());
        transferMessage.setMessageUserHead(isMySelf ? App.getApp().chatDataInfo.getPersonHead() : App.getApp().chatDataInfo.getOtherPersonHead());
        transferMessage.setLocalMessageImg(R.mipmap.type_zhuanzhang);
        Long transId = mAppDatabase.transferMessageDao().insert(transferMessage);

        //插入到外层的列表中
        WeixinChatInfo weixinChatInfo = new WeixinChatInfo();
        weixinChatInfo.setMainId(App.getApp().getMessageContent().getId());
        weixinChatInfo.setTypeIcon(R.mipmap.type_zhuanzhang);
        weixinChatInfo.setChatText(mTransferNumberEt.getText() + "元");
        weixinChatInfo.setType(type);
        weixinChatInfo.setChildTabId(transId);

        mAppDatabase.weixinChatInfoDao().insert(weixinChatInfo);

        finish();
    }

    @Override
    public void configDate(String selectDate) {
        Logger.i("select date --->" + selectDate);
        if (chooseType == 1) {
            mSendTransferTimeTv.setText(selectDate);
        } else {
            mReceiveTransferTimeTv.setText(selectDate);
        }
    }
}
