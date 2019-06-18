package com.yc.wsjt.model;

import com.yc.wsjt.base.IBaseRequestCallBack;

/**
 * Created by admin on 2017/4/7.
 */

public interface RoleInfoModel<T> {
    void getRoleList(IBaseRequestCallBack<T> iBaseRequestCallBack);
}
