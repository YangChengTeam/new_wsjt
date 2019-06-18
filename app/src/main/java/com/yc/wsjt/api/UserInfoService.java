package com.yc.wsjt.api;

import com.yc.wsjt.bean.ResultInfo;
import com.yc.wsjt.bean.UserInfoRet;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by admin on 2017/4/7.
 */

public interface UserInfoService {

    @POST("index/register")
    Observable<UserInfoRet> userInfo(@Body RequestBody requestBody);

    @POST("member/changeNickname")
    Observable<UserInfoRet> updateName(@Body RequestBody requestBody);
}
