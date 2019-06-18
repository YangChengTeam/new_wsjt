package com.yc.wsjt.base;

/**
 * Created by admin on 2017/4/7.
 */

public interface IBaseView<T> {
    /**
     * @descriptoin	请求前加载progress
     */
    void showProgress();

    /**
     * @descriptoin	请求结束之后隐藏progress
     */
    void dismissProgress();

    /**
     * @descriptoin	请求数据成功
     * @param tData 数据类型
     */
    void loadDataSuccess(T tData);

    /**
     * @descriptoin	请求数据错误
     * @param throwable 异常类型
     */
    void loadDataError(Throwable throwable);
}
