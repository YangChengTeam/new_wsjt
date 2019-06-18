package com.yc.wsjt.model;

import com.yc.wsjt.base.IBaseRequestCallBack;
import com.yc.wsjt.bean.UserInfo;

/**
 * Created by admin on 2017/4/7.
 */

public interface UserInfoModel<T> {
    void userInfo(UserInfo userInfo, IBaseRequestCallBack<T> iBaseRequestCallBack);
    void updateName(UserInfo userInfo, IBaseRequestCallBack<T> iBaseRequestCallBack);
}
