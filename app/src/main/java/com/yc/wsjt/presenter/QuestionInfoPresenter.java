package com.yc.wsjt.presenter;

import com.yc.wsjt.bean.QuestionInfo;

import java.io.File;

/**
 * Created by admin on 2017/4/7.
 */

public interface QuestionInfoPresenter {
    void addQuestion(QuestionInfo questionInfo, File file);
}
