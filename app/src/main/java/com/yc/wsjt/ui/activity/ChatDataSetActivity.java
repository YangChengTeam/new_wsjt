package com.yc.wsjt.ui.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.PhoneUtils;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.jaeger.library.StatusBarUtil;
import com.kyleduo.switchbutton.SwitchButton;
import com.orhanobut.logger.Logger;
import com.yc.wsjt.App;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.ChatDataInfo;
import com.yc.wsjt.presenter.Presenter;
import com.yc.wsjt.ui.custom.Glide4Engine;
import com.yc.wsjt.ui.custom.SettingRoleDialog;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhangdinghui on 2019/5/9.
 */
public class ChatDataSetActivity extends BaseActivity implements View.OnClickListener {

    private static final int REQUEST_CODE_CHOOSE = 1000;

    @BindView(R.id.tv_title)
    TextView mTitleTv;

    @BindView(R.id.btn_config)
    Button mConfigBtn;

    @BindView(R.id.tv_my_self_name)
    TextView mMySelfNameTv;

    @BindView(R.id.iv_my_head)
    ImageView mMySelfHeadIv;

    @BindView(R.id.tv_other_side_name)
    TextView mOtherSideNameTv;

    @BindView(R.id.iv_other_side_head)
    ImageView mOtherSideHeadIv;

    @BindView(R.id.iv_bg_img)
    ImageView mChatBgIv;

    @BindView(R.id.tv_friend_state)
    TextView mFriendStateTv;

    @BindView(R.id.sb_message)
    SwitchButton msBtn;

    @BindView(R.id.sb_receiver)
    SwitchButton receiverBtn;

    @BindView(R.id.sb_money_show)
    SwitchButton moneyShowBtn;

    SettingRoleDialog settingRoleDialog;

    BottomSheetDialog bottomSheetDialog;

    LinearLayout mIsFriendLayout;

    LinearLayout mIsBlackLayout;

    LinearLayout mIsDeleteLayout;

    ChatDataInfo mChatDataInfo;

    private File outputImage;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_chat_data_set;
    }

    @Override
    protected Presenter getPresenter() {
        return null;
    }

    @Override
    protected void initVars() {
        StatusBarUtil.setLightMode(this);
        mTitleTv.setText(getResources().getString(R.string.setting_ziliao));
        mConfigBtn.setVisibility(View.GONE);
    }

    @Override
    protected void initViews() {
        settingRoleDialog = new SettingRoleDialog(this, R.style.custom_dialog);

        bottomSheetDialog = new BottomSheetDialog(this);
        View friendStateView = LayoutInflater.from(this).inflate(R.layout.friend_state_view, null);
        mIsFriendLayout = friendStateView.findViewById(R.id.layout_friend);
        mIsBlackLayout = friendStateView.findViewById(R.id.layout_black);
        mIsDeleteLayout = friendStateView.findViewById(R.id.layout_delete);
        mIsFriendLayout.setOnClickListener(this);
        mIsBlackLayout.setOnClickListener(this);
        mIsDeleteLayout.setOnClickListener(this);
        bottomSheetDialog.setContentView(friendStateView);

        msBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                msBtn.setChecked(isChecked);
                if (mChatDataInfo != null) {
                    mAppDatabase.chatDataInfoDao().updateMessageState(isChecked, mChatDataInfo.getId());
                }
            }
        });

        receiverBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                receiverBtn.setChecked(isChecked);
                if (mChatDataInfo != null) {
                    mAppDatabase.chatDataInfoDao().updateReceiverState(isChecked, mChatDataInfo.getId());
                }
            }
        });

        moneyShowBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                moneyShowBtn.setChecked(isChecked);
                if (mChatDataInfo != null) {
                    mAppDatabase.chatDataInfoDao().updateMoneyState(isChecked, mChatDataInfo.getId());
                }
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            if (mAppDatabase.chatDataInfoDao().getItemById(PhoneUtils.getDeviceId()) != null) {
                mChatDataInfo = mAppDatabase.chatDataInfoDao().getItemById(PhoneUtils.getDeviceId());
                mMySelfNameTv.setText(mChatDataInfo.getPersonName());
                mOtherSideNameTv.setText(mChatDataInfo.getOtherPersonName());
                Glide.with(this).load(mChatDataInfo.getPersonHead()).into(mMySelfHeadIv);
                Glide.with(this).load(mChatDataInfo.getOtherPersonHead()).into(mOtherSideHeadIv);
                Glide.with(this).load(mChatDataInfo.getChatBgImage()).into(mChatBgIv);
                String tempStr = "好友";
                if (mChatDataInfo.getFriendType() == 0) {
                    tempStr = "好友";
                }
                if (mChatDataInfo.getFriendType() == 1) {
                    tempStr = "已加入黑名单";
                }
                if (mChatDataInfo.getFriendType() == 2) {
                    tempStr = "已删除好友";
                }
                mFriendStateTv.setText(tempStr);
                msBtn.setChecked(mChatDataInfo.messageDisturb);
                receiverBtn.setChecked(mChatDataInfo.receiverOpen);
                moneyShowBtn.setChecked(mChatDataInfo.showWeiXinMoney);
                App.getApp().chatDataInfo = mChatDataInfo;
            } else {
                mChatDataInfo = new ChatDataInfo();
                mChatDataInfo.setDeviceId(PhoneUtils.getDeviceId());
                mAppDatabase.chatDataInfoDao().insert(mChatDataInfo);
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.layout_set_my)
    void setMySelf() {
        if (settingRoleDialog != null && !settingRoleDialog.isShowing()) {
            settingRoleDialog.setType(0);
            settingRoleDialog.show();
        }
    }

    @OnClick(R.id.layout_set_other_side)
    void setOtherSide() {
        if (settingRoleDialog != null && !settingRoleDialog.isShowing()) {
            settingRoleDialog.setType(1);
            settingRoleDialog.show();
        }
    }

    @OnClick(R.id.layout_friend_state)
    void friendState() {
        if (bottomSheetDialog != null && !bottomSheetDialog.isShowing()) {
            bottomSheetDialog.show();
        }
    }

    @OnClick(R.id.layout_bg_image)
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
                        String tempPath = Matisse.obtainPathResult(data).get(0);
                        Logger.i("out path--->" + tempPath);
                        Glide.with(ChatDataSetActivity.this).load(tempPath).into(mChatBgIv);
                        if (mChatDataInfo != null) {
                            mAppDatabase.chatDataInfoDao().updateBgImage(tempPath, mChatDataInfo.getId());
                        }
                    }
                    break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        int tempType = 0;
        String tempStateStr = "好友";
        switch (v.getId()) {
            case R.id.layout_friend:
                tempType = 0;
                tempStateStr = "好友";
                break;
            case R.id.layout_black:
                tempType = 1;
                tempStateStr = "已加入黑名单";
                break;
            case R.id.layout_delete:
                tempType = 2;
                tempStateStr = "已删除好友";
                break;
            case R.id.layout_view_cancel:
                break;
            default:
                tempType = 0;
                break;
        }

        if (tempType > 0) {
            mFriendStateTv.setText(tempStateStr);
        }

        if (mChatDataInfo != null) {
            mAppDatabase.chatDataInfoDao().updateFriendState(tempType, mChatDataInfo.getId());
        }

        if (bottomSheetDialog != null && bottomSheetDialog.isShowing()) {
            bottomSheetDialog.dismiss();
        }
    }
}
