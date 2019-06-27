package com.yc.wsjt.model;

import android.content.Context;

import com.yc.wsjt.api.EmojiInfoService;
import com.yc.wsjt.base.BaseModel;
import com.yc.wsjt.base.IBaseRequestCallBack;
import com.yc.wsjt.bean.EmojiInfoRet;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by admin on 2017/4/7.
 */

public class EmojiInfoModelImp extends BaseModel implements EmojiInfoModel<EmojiInfoRet> {

    private Context context;
    private EmojiInfoService emojiInfoService;
    private CompositeSubscription mCompositeSubscription;

    public EmojiInfoModelImp(Context context) {
        this.context = context;
        emojiInfoService = mRetrofit.create(EmojiInfoService.class);
        mCompositeSubscription = new CompositeSubscription();
    }

    @Override
    public void getEmojiList(IBaseRequestCallBack<EmojiInfoRet> iBaseRequestCallBack) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), "");

        mCompositeSubscription.add(emojiInfoService.getEmojiList(requestBody)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<EmojiInfoRet>() {
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
                    public void onNext(EmojiInfoRet emojiInfoRet) {
                        //回调接口：请求成功，获取实体类对象
                        iBaseRequestCallBack.requestSuccess(emojiInfoRet);
                    }
                }));
    }
}