package com.yc.wsjt.model;

import com.yc.wsjt.base.IBaseRequestCallBack;

/**
 * Created by admin on 2017/4/7.
 */

public interface GifListModel<T> {
    void getGifList(int index, IBaseRequestCallBack<T> iBaseRequestCallBack);
}
