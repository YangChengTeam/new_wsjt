package com.yc.wsjt.model;

import com.yc.wsjt.base.IBaseRequestCallBack;
import com.yc.wsjt.bean.QuestionInfo;

import java.io.File;

/**
 * Created by admin on 2017/4/7.
 */

public interface QuestionInfoModel<T> {
    void addQuestion(QuestionInfo questionInfo,File file, IBaseRequestCallBack<T> iBaseRequestCallBack);
}
