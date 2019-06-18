package com.yc.wsjt.common;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by admin on 2017/3/10.
 */

public class RetrofitNoRsaManager {

    public static OkHttpClient mOkHttpClient;

    public static Retrofit mRetrofit;

    public static Retrofit retrofit() {
        initOkHttpClient();
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .client(mOkHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();

        }
        return mRetrofit;
    }

    public static void initOkHttpClient() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        if (mOkHttpClient == null) {
            synchronized (RetrofitManager.class) {
                if (mOkHttpClient == null) {
                    mOkHttpClient = new OkHttpClient.Builder()
                            //.addInterceptor(encryptionInterceptor)
                            //.addInterceptor(basicParamsInterceptor)
                            .addInterceptor(logging)
                            .build();
                }
            }
        }
    }
}
