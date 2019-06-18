package com.yc.wsjt.model;

import com.yc.wsjt.base.IBaseRequestCallBack;

/**
 * Created by admin on 2017/4/7.
 */

public interface HeadInfoModel<T> {
    void getHeadList(int index, IBaseRequestCallBack<T> iBaseRequestCallBack);
}
