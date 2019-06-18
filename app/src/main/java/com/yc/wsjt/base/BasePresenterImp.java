package com.yc.wsjt.base;

/**
 * 描述：
 * * 代理对象的基础实现 ：  一些基础的方法
 *
 * @param <V> 视图接口对象(view) 具体业务各自继承自IBaseView
 * @param <T> 业务请求返回的具体对象
 */
public class BasePresenterImp<V extends IBaseView, T> implements IBaseRequestCallBack<T> {

    //基类视图
    private IBaseView iBaseView = null;

    /**
     * @param view 具体业务的视图接口对象
     * @descriptoin 构造方法
     */
    public BasePresenterImp(V view) {
        this.iBaseView = view;
    }

    /**
     * @descriptoin 请求之前显示progress
     */
    @Override
    public void beforeRequest() {
        iBaseView.showProgress();
    }

    /**
     * @param throwable 异常信息
     * @descriptoin 请求异常显示异常信息
     */
    @Override
    public void requestError(Throwable throwable) {
        iBaseView.loadDataError(throwable);
        iBaseView.dismissProgress(); //请求错误，提示错误信息之后隐藏progress
    }

    /**
     * @descriptoin 请求完成之后隐藏progress
     */
    @Override
    public void requestComplete() {
        iBaseView.dismissProgress();
    }

    /**
     * @param callBack 回调的数据
     * @descriptoin 请求成功获取成功之后的数据信息
     */
    @Override
    public void requestSuccess(T callBack) {
        iBaseView.loadDataSuccess(callBack);
    }

}