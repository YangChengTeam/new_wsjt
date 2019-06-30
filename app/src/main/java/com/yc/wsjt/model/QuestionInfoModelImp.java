package com.yc.wsjt.model;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.yc.wsjt.api.QuestionInfoService;
import com.yc.wsjt.base.BaseNoRsaModel;
import com.yc.wsjt.base.IBaseRequestCallBack;
import com.yc.wsjt.bean.QuestionInfo;
import com.yc.wsjt.bean.ResultInfo;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by admin on 2017/4/7.
 */

public class QuestionInfoModelImp extends BaseNoRsaModel implements QuestionInfoModel<ResultInfo> {

    private Context context;
    private QuestionInfoService questionInfoService;
    private CompositeSubscription mCompositeSubscription;

    public QuestionInfoModelImp(Context context) {
        this.context = context;
        questionInfoService = mRetrofit.create(QuestionInfoService.class);
        mCompositeSubscription = new CompositeSubscription();
    }

    @Override
    public void addQuestion(QuestionInfo questionInfo, File file, IBaseRequestCallBack<ResultInfo> iBaseRequestCallBack) {
        MultipartBody.Part fileBody = null;
        if (file != null) {
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            // MultipartBody.Part  和后端约定好Key，这里的partName是用image
            fileBody = MultipartBody.Part.createFormData("pic", file.getName(), requestFile);
        }

        JSONObject params = new JSONObject();
        try {
            params.put("type", questionInfo.getType());
            params.put("desp", questionInfo.getContent());
            params.put("contact", questionInfo.getContact());
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), params.toString());

        RequestBody description =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), JSON.toJSONString(params));

        mCompositeSubscription.add(questionInfoService.addQuestion(description, fileBody != null ? fileBody : null)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<ResultInfo>() {
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
                    public void onNext(ResultInfo resultInfo) {
                        //回调接口：请求成功，获取实体类对象
                        iBaseRequestCallBack.requestSuccess(resultInfo);
                    }
                }));
    }
}