package com.yc.wsjt.base;

/**
 * Created by admin on 2017/3/10.
 * 描述：请求数据的回调接口
 * Presenter用于接受model获取（加载）数据后的回调
 */

public interface IBaseRequestCallBack<T> {
    /**
     * @descriptoin 请求之前的操作
     */
    void beforeRequest();

    /**
     * @param throwable 异常类型
     * @descriptoin 请求异常
     */
    void requestError(Throwable throwable);

    /**
     * @descriptoin 请求完成
     */
    void requestComplete();

    /**
     * @param callBack 根据业务返回相应的数据
     * @descriptoin 请求成功
     */
    void requestSuccess(T callBack);
}
