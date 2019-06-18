package com.yc.wsjt.ui.custom;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.orhanobut.logger.Logger;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.yc.wsjt.App;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.UserInfo;
import com.yc.wsjt.bean.UserInfoRet;
import com.yc.wsjt.common.Constants;
import com.yc.wsjt.presenter.UserInfoPresenterImp;
import com.yc.wsjt.view.UserInfoView;

import java.util.Map;

import es.dmoral.toasty.Toasty;


public class LoginDialog extends Dialog implements View.OnClickListener, UserInfoView {

    private Context mContext;

    ImageView mCloseIv;

    LinearLayout mWeiXinLayout;

    LinearLayout mQQLayout;

    private UserInfoPresenterImp userInfoPresenterImp;

    private UMShareAPI mShareAPI = null;

    private ProgressDialog progressDialog = null;

    private int loginType = 1; //1:微信.2:QQ

    public LoginDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public LoginDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_dialog_view);
        setCanceledOnTouchOutside(true);

        mShareAPI = UMShareAPI.get(mContext);

        userInfoPresenterImp = new UserInfoPresenterImp(this, mContext);
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("正在登录");

        initView();
    }

    private void initView() {
        mCloseIv = findViewById(R.id.iv_close);
        mWeiXinLayout = findViewById(R.id.layout_weixin);
        mQQLayout = findViewById(R.id.layout_qq);
        mWeiXinLayout.setOnClickListener(this);
        mQQLayout.setOnClickListener(this);
        mCloseIv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_weixin:
                if (progressDialog != null && !progressDialog.isShowing()) {
                    progressDialog.show();
                }
                loginType = 1;
                mShareAPI.getPlatformInfo((Activity) mContext, SHARE_MEDIA.WEIXIN, authListener);
                this.dismiss();
                break;
            case R.id.layout_qq:
                if (progressDialog != null && !progressDialog.isShowing()) {
                    progressDialog.show();
                }
                loginType = 2;
                mShareAPI.getPlatformInfo((Activity) mContext, SHARE_MEDIA.QQ, authListener);
                this.dismiss();
                break;
            case R.id.iv_close:
                this.dismiss();
                break;
        }
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
    public void loadDataSuccess(UserInfoRet tData) {
        Logger.i("login dialog user info --->" + JSONObject.toJSONString(tData));

        if (tData != null && tData.getCode() == Constants.SUCCESS) {
            if (tData instanceof UserInfoRet) {
                ToastUtils.showLong("登录成功");

                UserInfo userInfo = tData.getData();
                App.getApp().setmUserInfo(userInfo);
                App.getApp().setLogin(true);
                SPUtils.getInstance().put(Constants.USER_INFO, JSONObject.toJSONString(tData.getData()));
            }
        } else {
            //ToastUtils.showLong(StringUtils.isEmpty(tData.getMsg()) ? "登录失败" : tData.getMsg());
            ToastUtils.showLong("登录失败");
            Logger.i(StringUtils.isEmpty(tData.getMsg()) ? "登录失败" : tData.getMsg());
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
    }

    @Override
    public void loadDataError(Throwable throwable) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    UMAuthListener authListener = new UMAuthListener() {
        /**
         * @desc 授权开始的回调
         * @param platform 平台名称
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @desc 授权成功的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param data 用户资料返回
         */
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Logger.i("auth data --->" + JSONObject.toJSONString(data));
            //Toast.makeText(mContext, "授权成功了", Toast.LENGTH_LONG).show();
            App.isLoginAuth = true;
            if (data != null) {
                if (progressDialog != null && !progressDialog.isShowing()) {
                    progressDialog.show();
                }

                UserInfo threeUserInfo = new UserInfo();
                threeUserInfo.setOpenId(data.get("uid").toUpperCase());//openid全部大写
                threeUserInfo.setRegType(loginType);
                threeUserInfo.setNickName(data.get("name"));
                threeUserInfo.setSex(data.get("gender").equals("男") ? 1 : 2);
                threeUserInfo.setFace(data.get("iconurl"));
                threeUserInfo.setDeviceId(DeviceUtils.getAndroidID());
                userInfoPresenterImp.userInfo(threeUserInfo);
            }
        }

        /**
         * @desc 授权失败的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            //Toast.makeText(mContext, "授权失败：" + t.getMessage(), Toast.LENGTH_LONG).show();
            Toasty.normal(mContext, "授权失败").show();
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }

        /**
         * @desc 授权取消的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         */
        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toasty.normal(mContext, "授权取消").show();
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
    };
}