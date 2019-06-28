package com.yc.wsjt.api;

import com.yc.wsjt.bean.UserInfoRet;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

/**
 * Created by admin on 2017/4/7.
 */

public interface QuestionInfoService {

    @Multipart
    @POST("index/feedback")
    Observable<UserInfoRet> addQuestion(@Part("question") RequestBody requestBody,
                                       @Part MultipartBody.Part file);
}
