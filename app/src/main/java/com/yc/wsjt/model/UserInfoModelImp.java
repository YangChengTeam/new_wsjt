package com.yc.wsjt.model;

import android.content.Context;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.PhoneUtils;
import com.yc.wsjt.api.UserInfoService;
import com.yc.wsjt.base.BaseModel;
import com.yc.wsjt.base.IBaseRequestCallBack;
import com.yc.wsjt.bean.UserInfo;
import com.yc.wsjt.bean.UserInfoRet;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by admin on 2017/4/7.
 */

public class UserInfoModelImp extends BaseModel implements UserInfoModel<UserInfoRet> {

    private Context context;
    private UserInfoService userInfoService;
    private CompositeSubscription mCompositeSubscription;

    public UserInfoModelImp(Context context) {
        this.context = context;
        userInfoService = mRetrofit.create(UserInfoService.class);
        mCompositeSubscription = new CompositeSubscription();
    }

    @Override
    public void userInfo(UserInfo userInfo, final IBaseRequestCallBack<UserInfoRet> iBaseRequestCallBack) {

        JSONObject params = new JSONObject();
        try {
            params.put("nickname", userInfo.getNickName());
            params.put("openid", userInfo.getOpenId());
            //params.put("reg_time", TimeUtils.date2Millis(new Date()));
            params.put("reg_type", userInfo.getRegType());
            params.put("face", userInfo.getFace());
            params.put("sex", userInfo.getSex());
            params.put("device", PhoneUtils.getIMEI());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), params.toString());

        mCompositeSubscription.add(userInfoService.userInfo(requestBody)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<UserInfoRet>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        //onStart它总是在 subscribe 所发生的线程被调用 ,如果你的subscribe不是主线程，则会出错，则需要指定线程;
                        iBaseRequestCallBack.beforeRequest();
                    }

                    @Override
                    public void onCompleted() {
                        //回调接口：请求已完成，可以隐藏progress
                        iBaseRequestCallBack.requestComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        //回调接口：请求异常
                        iBaseRequestCallBack.requestError(e);
                    }

                    @Override
                    public void onNext(UserInfoRet userInfoRet) {
                        //回调接口：请求成功，获取实体类对象
                        iBaseRequestCallBack.requestSuccess(userInfoRet);
                    }
                }));
    }

    @Override
    public void updateName(UserInfo userInfo, IBaseRequestCallBack<UserInfoRet> iBaseRequestCallBack) {
        JSONObject params = new JSONObject();
        try {
            params.put("nickname", userInfo.getNickName());
            params.put("uid", userInfo.getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), params.toString());

        mCompositeSubscription.add(userInfoService.updateName(requestBody)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<UserInfoRet>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        //onStart它总是在 subscribe 所发生的线程被调用 ,如果你的subscribe不是主线程，则会出错，则需要指定线程;
                        iBaseRequestCallBack.beforeRequest();
                    }

                    @Override
                    public void onCompleted() {
                        //回调接口：请求已完成，可以隐藏progress
                        iBaseRequestCallBack.requestComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        //回调接口：请求异常
                        iBaseRequestCallBack.requestError(e);
                    }

                    @Override
                    public void onNext(UserInfoRet userInfoRet) {
                        //回调接口：请求成功，获取实体类对象
                        iBaseRequestCallBack.requestSuccess(userInfoRet);
                    }
                }));
    }
}
