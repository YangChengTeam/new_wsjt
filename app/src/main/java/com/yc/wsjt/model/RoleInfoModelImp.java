package com.yc.wsjt.model;

import android.content.Context;

import com.yc.wsjt.api.RoleInfoService;
import com.yc.wsjt.base.BaseModel;
import com.yc.wsjt.base.IBaseRequestCallBack;
import com.yc.wsjt.bean.RoleInfoRet;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by admin on 2017/4/7.
 */

public class RoleInfoModelImp extends BaseModel implements RoleInfoModel<RoleInfoRet> {

    private Context context;
    private RoleInfoService roleInfoService;
    private CompositeSubscription mCompositeSubscription;

    public RoleInfoModelImp(Context context) {
        this.context = context;
        roleInfoService = mRetrofit.create(RoleInfoService.class);
        mCompositeSubscription = new CompositeSubscription();
    }

    @Override
    public void getRoleList(IBaseRequestCallBack<RoleInfoRet> iBaseRequestCallBack) {

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), "");

        mCompositeSubscription.add(roleInfoService.getRoleList(requestBody)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<RoleInfoRet>() {
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
                    public void onNext(RoleInfoRet roleInfoRet) {
                        //回调接口：请求成功，获取实体类对象
                        iBaseRequestCallBack.requestSuccess(roleInfoRet);
                    }
                }));
    }

}
