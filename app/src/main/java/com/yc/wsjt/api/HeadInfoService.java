package com.yc.wsjt.api;

import com.yc.wsjt.bean.HeadInfoRet;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by admin on 2017/4/7.
 */

public interface HeadInfoService {

    @POST("index/index")
    Observable<HeadInfoRet> getHeadList(@Body RequestBody requestBody);
}
