package com.yc.wsjt.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jaeger.library.StatusBarUtil;
import com.orhanobut.logger.Logger;
import com.yc.wsjt.App;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.MessageEvent;
import com.yc.wsjt.bean.UserInfo;
import com.yc.wsjt.bean.UserInfoRet;
import com.yc.wsjt.common.Constants;
import com.yc.wsjt.presenter.UserInfoPresenterImp;
import com.yc.wsjt.ui.activity.AboutActivity;
import com.yc.wsjt.ui.activity.FeedBackActivity;
import com.yc.wsjt.ui.activity.KeFuHelpActivity;
import com.yc.wsjt.ui.activity.SettingActivity;
import com.yc.wsjt.ui.activity.UpdateUserInfoActivity;
import com.yc.wsjt.ui.activity.VipInfoActivity;
import com.yc.wsjt.ui.custom.GlideCircleTransformWithBorder;
import com.yc.wsjt.ui.custom.LoginDialog;
import com.yc.wsjt.ui.custom.OpenVipDialog;
import com.yc.wsjt.ui.custom.VipPayTypeDialog;
import com.yc.wsjt.view.UserInfoView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by admin on 2017/4/20.
 */

public class MyFragment extends BaseFragment implements UserInfoView, OpenVipDialog.VipListener, VipPayTypeDialog.PayListener {

    @BindView(R.id.layout_setting)
    RelativeLayout mSettingLayout;

    @BindView(R.id.iv_user_head)
    ImageView mUserHeadIv;

    @BindView(R.id.tv_nick_name)
    TextView mNickNameTv;

    @BindView(R.id.tv_user_id)
    TextView mUserIdTv;

    @BindView(R.id.tv_state)
    TextView mVipStateTv;

    @BindView(R.id.fake_status_bar)
    View mFakeView;

    OpenVipDialog openVipDialog;

    LoginDialog loginDialog;

    VipPayTypeDialog vipPayTypeDialog;

    private UserInfo mUserInfo;

    private UserInfoPresenterImp userInfoPresenterImp;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }


    @Override
    protected int getContentView() {
        return R.layout.fragment_my;
    }

    @Override
    public void initVars() {

    }

    public void setTopViewBgColor() {
        try {
            StatusBarUtil.setTransparentForImageView(getActivity(), null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initViews() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, BarUtils.getStatusBarHeight());
        mFakeView.setLayoutParams(params);

        openVipDialog = new OpenVipDialog(getActivity(), R.style.custom_dialog);
        openVipDialog.setVipListener(this);

        loginDialog = new LoginDialog(getActivity(), R.style.custom_dialog);
        vipPayTypeDialog = new VipPayTypeDialog(getActivity(), R.style.custom_dialog);
        vipPayTypeDialog.setPayListener(this);

        userInfoPresenterImp = new UserInfoPresenterImp(this, getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();
        Logger.i("myfragment ---onResume");
        //如果登录过，从本地获取数据
        initUserInfo();
        if (mUserInfo != null) {
            userInfoPresenterImp.userInfo(mUserInfo);
        } else {
            RequestOptions options = new RequestOptions();
            options.transform(new GlideCircleTransformWithBorder(getActivity(), 2, ContextCompat.getColor(getActivity(), R.color.white)));
            Glide.with(getActivity()).load(R.mipmap.no_login_def).apply(options).into(mUserHeadIv);
        }
    }

    public void initUserInfo() {
        if (!StringUtils.isEmpty(SPUtils.getInstance().getString(Constants.USER_INFO))) {
            Logger.i(SPUtils.getInstance().getString(Constants.USER_INFO));
            mUserInfo = JSON.parseObject(SPUtils.getInstance().getString(Constants.USER_INFO), new TypeReference<UserInfo>() {
            });
            App.getApp().isLogin = true;
            if (mUserInfo != null) {
                mVipStateTv.setText(mUserInfo.getStatus() == 2 ? "已开通" : "试用会员");
                if (mUserInfo.getStatus() == 1) {
                    mVipStateTv.setText("未开通");
                } else {
                    mVipStateTv.setText(mUserInfo.getStatus() == 2 ? "已开通" : "试用会员");
                }
                //设置用户信息
                mNickNameTv.setText(StringUtils.isEmpty(mUserInfo.getNickName()) ? "" : mUserInfo.getNickName());
                mUserIdTv.setText(StringUtils.isEmpty(mUserInfo.getId()) ? "用户ID：xxxx" : "用户ID：" + mUserInfo.getId());

                RequestOptions options = new RequestOptions();
                options.transform(new GlideCircleTransformWithBorder(getActivity(), 2, ContextCompat.getColor(getActivity(), R.color.white)));
                Glide.with(getActivity()).load(mUserInfo.getFace()).apply(options).into(mUserHeadIv);
            } else {
                mVipStateTv.setText("未开通");
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        if (event != null && event.isLoginSuccess()) {
            Logger.i("login success--->");
            initUserInfo();
        }
    }

    @Override
    public void loadData() {

    }

    @Override
    protected void initFragmentConfig() {

    }

    @OnClick(R.id.layout_kefu)
    void kefu() {
        Intent intent = new Intent(getActivity(), KeFuHelpActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.layout_feed_back)
    void feedBack() {
        Intent intent = new Intent(getActivity(), FeedBackActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.layout_user_info)
    void userInfo() {
        if (App.getApp().isLogin) {
            Intent intent = new Intent(getActivity(), UpdateUserInfoActivity.class);
            startActivity(intent);
        } else {
            if (loginDialog != null && !loginDialog.isShowing()) {
                loginDialog.show();
            }
        }
    }

    @OnClick(R.id.layout_setting)
    void setting() {
        Intent intent = new Intent(getActivity(), SettingActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.layout_about_us)
    void aboutUs() {
        Intent intent = new Intent(getActivity(), AboutActivity.class);
        startActivity(intent);
    }

    @Override
    public void addComment() {

    }

    @Override
    public void openVip() {
        if (vipPayTypeDialog != null && !vipPayTypeDialog.isShowing()) {
            vipPayTypeDialog.show();
        }
    }

    @Override
    public void closeOpenVip() {

    }

    @OnClick(R.id.layout_vip_info)
    void openVipView() {
        if (App.getApp().isLogin && mUserInfo != null) {
            if (mUserInfo.getStatus() > 1) {
                Intent intent = new Intent(getActivity(), VipInfoActivity.class);
                startActivity(intent);
            } else {
                if (openVipDialog != null && !openVipDialog.isShowing()) {
                    openVipDialog.show();
                }
            }
        } else {
            if (loginDialog != null && !loginDialog.isShowing()) {
                loginDialog.show();
            }
        }
    }

    @Override
    public void pay() {
        ToastUtils.showLong("点击了支付");
    }


    @Override
    public void showProgress() {

    }

    @Override
    public void dismissProgress() {

    }

    @Override
    public void loadDataSuccess(UserInfoRet tData) {
        Logger.i("myfragment user info --->" + JSONObject.toJSONString(tData));

        if (tData != null && tData.getCode() == Constants.SUCCESS) {
            if (tData instanceof UserInfoRet) {

                App.getApp().setmUserInfo(tData.getData());
                App.getApp().setLogin(true);
                SPUtils.getInstance().put(Constants.USER_INFO, JSONObject.toJSONString(tData.getData()));
                //设置用户信息
                mNickNameTv.setText(StringUtils.isEmpty(tData.getData().getNickName()) ? "" : tData.getData().getNickName());
                mUserIdTv.setText(StringUtils.isEmpty(tData.getData().getId()) ? "用户ID：xxxx" : "用户ID：" + tData.getData().getId());

                RequestOptions options = new RequestOptions();
                options.transform(new GlideCircleTransformWithBorder(getActivity(), 2, ContextCompat.getColor(getActivity(), R.color.white)));
                Glide.with(getActivity()).load(tData.getData().getFace()).apply(options).into(mUserHeadIv);
            }
        } else {
            //ToastUtils.showLong(StringUtils.isEmpty(tData.getMsg()) ? "登录失败" : tData.getMsg());
            ToastUtils.showLong("登录失败");
            Logger.i(StringUtils.isEmpty(tData.getMsg()) ? "登录失败" : tData.getMsg());
        }
    }

    @Override
    public void loadDataError(Throwable throwable) {

    }

    @OnClick(R.id.layout_logout)
    void logout() {
        if (App.getApp().isLogin && mUserInfo != null) {
            ToastUtils.showLong("已退出");
            App.getApp().setmUserInfo(null);
            App.getApp().setLogin(false);
            SPUtils.getInstance().put(Constants.USER_INFO, "");
            mNickNameTv.setText("请登录");
            mUserIdTv.setText("请点击登录");
            mVipStateTv.setText("未开通");
            RequestOptions options = new RequestOptions();
            options.transform(new GlideCircleTransformWithBorder(getActivity(), 2, ContextCompat.getColor(getActivity(), R.color.white)));
            Glide.with(getActivity()).load(R.mipmap.no_login_def).apply(options).into(mUserHeadIv);
        }
    }

    @Override
    public void payClose() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
