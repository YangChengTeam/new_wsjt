package com.yc.wsjt.api;

import com.yc.wsjt.bean.RoleInfoRet;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by admin on 2017/4/7.
 */

public interface RoleInfoService {

    @POST("index/role")
    Observable<RoleInfoRet> getRoleList(@Body RequestBody requestBody);
}
