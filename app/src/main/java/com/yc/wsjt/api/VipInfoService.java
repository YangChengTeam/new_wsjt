package com.yc.wsjt.api;

import com.yc.wsjt.bean.VipInfoRet;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by admin on 2017/4/7.
 */

public interface VipInfoService {

    @POST("goods/index")
    Observable<VipInfoRet> getVipList(@Body RequestBody requestBody);
}
