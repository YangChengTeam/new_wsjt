package com.yc.wsjt.model;

import com.yc.wsjt.base.IBaseRequestCallBack;

import java.io.File;

/**
 * Created by iflying on 2018/1/9.
 */

public interface UpdateInfoModel<T> {
    void updateHead(String uid, File file, IBaseRequestCallBack<T> iBaseRequestCallBack);
}
