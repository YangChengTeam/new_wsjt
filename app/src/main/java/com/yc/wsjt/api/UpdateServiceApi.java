package com.yc.wsjt.api;

import com.yc.wsjt.bean.UserInfoRet;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

/**
 * Created by admin on 2017/3/13.
 */

public interface UpdateServiceApi {

    @Multipart
    @POST("member/changeAvatar")
    Observable<UserInfoRet> updateHead(@Part("uid") RequestBody requestBody,
                                       @Part MultipartBody.Part file);
}
