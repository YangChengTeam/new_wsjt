package com.yc.wsjt.ui.activity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.jaeger.library.StatusBarUtil;
import com.yc.wsjt.App;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.MessageContent;
import com.yc.wsjt.bean.PersonMessage;
import com.yc.wsjt.bean.WeixinChatInfo;
import com.yc.wsjt.bean.WeixinQunChatInfo;
import com.yc.wsjt.common.Constants;
import com.yc.wsjt.presenter.Presenter;
import com.yc.wsjt.ui.custom.InputDialog;
import com.yc.wsjt.ui.custom.SettingRoleDialog;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

public class ChatPersonCardActivity extends BaseActivity implements InputDialog.NumberListener {

    public static final int REQUEST_CODE_EMOJI = 1;

    @BindView(R.id.tv_title)
    TextView mTitleTv;

    @BindView(R.id.btn_config)
    Button mConfigBtn;

    @BindView(R.id.layout_send_info)
    RelativeLayout mSendInfoLayout;

    @BindView(R.id.tv_send_user_name)
    TextView mSendUserNameTv;

    @BindView(R.id.iv_send_user_head)
    ImageView mSendUserHeadIv;

    @BindView(R.id.tv_person_name)
    TextView mPersonNameTv;

    @BindView(R.id.iv_person_head)
    ImageView mPersonHeadIv;

    @BindView(R.id.tv_weixin_number)
    TextView mWeixinNumberTv;

    private File outputImage;

    InputDialog inputDialog;

    SettingRoleDialog settingRoleDialog;

    boolean isMySelf = true;

    private boolean isQunLiao;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_chat_person_card;
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
        inputDialog = new InputDialog(this, R.style.scale_dialog);
        inputDialog.setNumberListener(this);

        settingRoleDialog = new SettingRoleDialog(this, R.style.custom_dialog);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            isQunLiao = bundle.getBoolean("is_qunliao", false);
        }
        mTitleTv.setText("个人名片");

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

    @Override
    protected void onResume() {
        super.onResume();
        if (App.getApp().getTempPerson() != null) {
            Glide.with(this).load(App.getApp().getTempPerson().mHead).into(mPersonHeadIv);
            mPersonNameTv.setText(App.getApp().getTempPerson().mName);
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

    @OnClick(R.id.layout_choose_person)
    void choosePerson() {
        if (settingRoleDialog != null && !settingRoleDialog.isShowing()) {
            settingRoleDialog.setType(2);
            settingRoleDialog.show();
        }
    }

    @OnClick(R.id.layout_weixin_number)
    void weixinNumber() {
        inputDialog.show();

        WindowManager.LayoutParams windowParams = inputDialog.getWindow().getAttributes();
        windowParams.width = (int) (ScreenUtils.getScreenWidth() * 0.75);
        windowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        inputDialog.getWindow().setAttributes(windowParams);
    }

    @Override
    public void configNumber(String number) {
        mWeixinNumberTv.setText(number);
    }

    @OnClick(R.id.btn_config)
    void config() {
        int type = MessageContent.SEND_PERSON_CARD;
        if (!SPUtils.getInstance().getBoolean(Constants.IS_SELF, true)) {
            type = MessageContent.RECEIVE_PERSON_CARD;
        }

        //插入一条时间设置记录
        PersonMessage personMessage = new PersonMessage();
        personMessage.setWxMainId(App.getApp().getMessageContent().getWxMainId());
        personMessage.setPersonHeadImg(App.getApp().getTempPerson().mHead);
        personMessage.setPersonName(App.getApp().getTempPerson().mName);
        personMessage.setMessageType(type);
        personMessage.setMessageUserName(isMySelf ? App.getApp().chatDataInfo.getPersonName() : App.getApp().chatDataInfo.getOtherPersonName());
        personMessage.setMessageUserHead(isMySelf ? App.getApp().chatDataInfo.getPersonHead() : App.getApp().chatDataInfo.getOtherPersonHead());
        personMessage.setWeixinNumber(mWeixinNumberTv.getText().toString());
        Long personId = mAppDatabase.personMessageDao().insert(personMessage);

        if (isQunLiao) {
            //插入到外层的列表中
            WeixinQunChatInfo weixinQunChatInfo = new WeixinQunChatInfo();
            weixinQunChatInfo.setWxMainId(App.getApp().getMessageContent().getWxMainId());
            weixinQunChatInfo.setTypeIcon(R.mipmap.type_persion_card);
            weixinQunChatInfo.setType(type);
            weixinQunChatInfo.setChildTabId(personId);
            mAppDatabase.weixinQunChatInfoDao().insert(weixinQunChatInfo);
        } else {
            //插入到外层的列表中
            WeixinChatInfo weixinChatInfo = new WeixinChatInfo();
            weixinChatInfo.setWxMainId(App.getApp().getMessageContent().getWxMainId());
            weixinChatInfo.setTypeIcon(R.mipmap.type_persion_card);
            weixinChatInfo.setType(type);
            weixinChatInfo.setChildTabId(personId);
            mAppDatabase.weixinChatInfoDao().insert(weixinChatInfo);
        }
        finish();
    }

    @OnClick(R.id.iv_back)
    void back() {
        finish();
    }
}
