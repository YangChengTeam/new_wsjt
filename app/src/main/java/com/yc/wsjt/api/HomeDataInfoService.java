package com.yc.wsjt.api;

import com.yc.wsjt.bean.HomeDateInfoRet;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by admin on 2017/4/7.
 */

public interface HomeDataInfoService {

    @POST("index/index")
    Observable<HomeDateInfoRet> getHomeList(@Body RequestBody requestBody);
}
